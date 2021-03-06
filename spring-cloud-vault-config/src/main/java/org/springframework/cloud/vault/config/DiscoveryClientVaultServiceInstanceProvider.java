/*
 * Copyright 2018-2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.cloud.vault.config;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;

/**
 * Provider for {@link ServiceInstance} to look up the Vault service.
 *
 * @author Mark Paluch
 * @since 1.1
 */
public class DiscoveryClientVaultServiceInstanceProvider implements VaultServiceInstanceProvider {

	private static final Log log = LogFactory.getLog(DiscoveryClientVaultServiceInstanceProvider.class);

	private final DiscoveryClient client;

	public DiscoveryClientVaultServiceInstanceProvider(DiscoveryClient client) {
		this.client = client;
	}

	@Override
	public ServiceInstance getVaultServerInstance(String serviceId) {

		log.debug("Locating Vault server (" + serviceId + ") via discovery");

		List<ServiceInstance> instances = this.client.getInstances(serviceId);

		if (instances.isEmpty()) {
			throw new IllegalStateException("No instances found of Vault server (" + serviceId + ")");
		}

		ServiceInstance instance = instances.get(0);

		log.debug("Located Vault server (" + serviceId + ") via discovery: " + instance);

		return instance;
	}

}
