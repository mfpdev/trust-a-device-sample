/**
 *    © Copyright 2016 IBM Corp.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.github.mfpdev.sample.qrcodeweblogin;

import com.ibm.json.java.JSONObject;
import com.ibm.mfp.adapter.api.ConfigurationAPI;
import com.ibm.mfp.adapter.api.OAuthSecurity;
import com.ibm.mfp.server.registration.external.model.AuthenticatedUser;
import com.ibm.mfp.server.registration.external.model.ClientData;
import com.ibm.mfp.server.security.external.resource.AdapterSecurityContext;
import com.ibm.mfp.server.security.external.resource.ClientSearchCriteria;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Path("/")
public class QRCodeResource {
    static Logger logger = Logger.getLogger(QRCodeResource.class.getName());

    @Context
    private AdapterSecurityContext securityContext;

    @Context
    private ConfigurationAPI configurationAPI;

    CloseableHttpClient httpclient = HttpClients.createDefault();


    @Path("/approveWebUser")
    @POST
    @OAuthSecurity(scope = "UserLogin")
    @Produces("application/json")
    public Boolean approveUser(@QueryParam("uuid") String uuid) {
        ClientSearchCriteria clientSearchCriteria = new ClientSearchCriteria().byAttribute(QRCodeWebLoginSecurityCheck.QR_CODE_UUID, uuid);
        List<ClientData> clientsData = securityContext.findClientRegistrationData(clientSearchCriteria);
        if (clientsData.size() == 1) {
            clientsData.get(0).getPublicAttributes().put(QRCodeWebLoginSecurityCheck.WEB_USER_REGISTRATION_KEY, this.securityContext.getAuthenticatedUser());
            securityContext.storeClientRegistrationData(clientsData.get(0));
            return sendRefreshEvent(uuid);
        }

        return false;
    }

    /**
     * Notifying the web client that refresh is needed
     * @param uuid
     */
    private boolean sendRefreshEvent(String uuid) {
        String url = configurationAPI.getPropertyValue("webURL");
        if (!url.isEmpty()) {
            url = url + "/refresh/" + uuid;
            HttpGet httpGet = new HttpGet(url);
            try {
                CloseableHttpResponse response = httpclient.execute(httpGet);
                return response.getStatusLine().getStatusCode() == HttpStatus.SC_OK;
            } catch (IOException e) {
                logger.info("Cannot send refresh event to " + url);
            }
        }
        return false;
    }

    @Path("/user")
    @GET
    @OAuthSecurity (scope = "qrcode")
    @Produces("application/json")
    public AuthenticatedUser getUser() {
        return securityContext.getAuthenticatedUser();
    }

}



