/*
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

import com.ibm.mfp.adapter.api.ConfigurationAPI;
import com.ibm.mfp.adapter.api.OAuthSecurity;
import com.ibm.mfp.server.registration.external.model.AuthenticatedUser;
import com.ibm.mfp.server.registration.external.model.ClientData;
import com.ibm.mfp.server.security.external.resource.AdapterSecurityContext;
import com.ibm.mfp.server.security.external.resource.ClientSearchCriteria;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

@Api(value = "QR Code Web Login")
@Path("/")
public class QRCodeResource {
    private static final String WEB_URL_FOR_NOTIFY = "webURLForNotify";
    private static Logger logger = Logger.getLogger(QRCodeResource.class.getName());

    @Context
    private AdapterSecurityContext securityContext;

    @Context
    private ConfigurationAPI configurationAPI;

    private CloseableHttpClient httpclient = HttpClients.createDefault();


    @Path("/approveWebUser")
    @POST
    @OAuthSecurity(scope = "UserLogin")
    @Produces("application/json")
    @ApiOperation(value = "Approve web user by mobile app",
            notes = "Let mobile app approve web client by finding the client in the registration service",
            httpMethod = "POST",
            response = Void.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Return true if user",
                    response = Boolean.class)
    })
    public Boolean approveWebUser(@QueryParam("uuid") String uuid) {
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
     * @param uuid - the uuid which belong to client that need refresh
     */
    private boolean sendRefreshEvent(String uuid) {
        String url = configurationAPI.getPropertyValue(WEB_URL_FOR_NOTIFY);
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
    @ApiOperation(value = "Get authenticated user resource",
            notes = "Sample resource which protected with qrcode security check",
            httpMethod = "GET",
            response = Void.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Return true if user",
                    response = Boolean.class)
    })
    public AuthenticatedUser getUser() {
        return securityContext.getAuthenticatedUser();
    }

}



