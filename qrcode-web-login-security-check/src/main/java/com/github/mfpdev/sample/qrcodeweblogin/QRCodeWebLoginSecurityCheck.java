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

import com.ibm.mfp.security.checks.base.UserAuthenticationSecurityCheck;
import com.ibm.mfp.server.registration.external.model.AuthenticatedUser;
import net.glxn.qrgen.core.image.ImageType;
import net.glxn.qrgen.javase.QRCode;
import org.apache.commons.codec.binary.Base64;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class QRCodeWebLoginSecurityCheck extends UserAuthenticationSecurityCheck {

	static final String QR_CODE_UUID = "qrCodeUUID";
	static final String WEB_USER_REGISTRATION_KEY = "webUser";
	private static final int QR_CODE_IMAGE_SIZE = 300;

	private String userId, displayName;

	@Override
	protected AuthenticatedUser createUser() {
		return new AuthenticatedUser(userId, displayName, this.getName());
	}

	/**
	 * validateCredentials - Validate the credential by looking if web user is in registered
	 * @param map
	 * @return
     */
	protected boolean validateCredentials(Map<String, Object> map) {
		AuthenticatedUser webUser = getWebUser();
		if (webUser != null) {
			userId = webUser.getId();
			displayName = webUser.getDisplayName();
			removeWebUser();
			return true;
		} else {
			return false;
		}
	}

	/**
	 * createChallenge - create the QRCode image
	 * @return the challenge
     */
	protected Map<String, Object> createChallenge() {
		AuthenticatedUser webUser = getWebUser();

		Map <String, Object> challenge = new HashMap<> ();
		if (webUser != null) {
			challenge.put("isRegistered", true);
		} else {
			String qrUUID = UUID.randomUUID().toString();
			registrationContext.getRegisteredPublicAttributes().put(QR_CODE_UUID, qrUUID);

			ByteArrayOutputStream stream = QRCode.from(qrUUID).to(ImageType.JPG).withSize(QR_CODE_IMAGE_SIZE,QR_CODE_IMAGE_SIZE).stream();
			String encodedImage = Base64.encodeBase64String(stream.toByteArray());
			challenge.put("qrCode", encodedImage);
			challenge.put("qrUUID", qrUUID);
		}
		return challenge;
	}

	/**
	 * Return the web used from the registration service
	 * @return AuthenticatedUser
     */
	private AuthenticatedUser getWebUser () {
		return registrationContext.getRegisteredPublicAttributes().get(WEB_USER_REGISTRATION_KEY, AuthenticatedUser.class);
	}

	/**
	 * Remove the web used from the registration service
	 * @return AuthenticatedUser
	 */
	private void removeWebUser() {
		registrationContext.getRegisteredPublicAttributes().put(WEB_USER_REGISTRATION_KEY, null);
	}
 }
