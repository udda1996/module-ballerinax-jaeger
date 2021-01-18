/*
 * Copyright (c) 2020, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package io.ballerina.observe.trace.jaeger;

import io.ballerina.observe.trace.jaeger.backend.ContainerizedJaegerServer;
import io.ballerina.observe.trace.jaeger.backend.JaegerServer;
import io.ballerina.observe.trace.jaeger.backend.ProcessJaegerServer;
import org.ballerinalang.test.context.BalServer;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

/**
 * Parent test class for all extension integration tests cases.
 *
 * This will provide basic functionality for integration tests.
 * This will initialize a single ballerina instance which will be used by all the test cases throughout.
 */
public class BaseTestCase {
    static BalServer balServer;
    static JaegerServer jaegerServer;

    @BeforeSuite(alwaysRun = true)
    public void initialize() throws Exception {
        balServer = new BalServer();
        if (System.getenv().containsKey(ProcessJaegerServer.EXECUTABLE_ENV_VAR_KEY)) {
            jaegerServer = new ProcessJaegerServer();
        } else {
            jaegerServer = new ContainerizedJaegerServer();
        }
    }

    @AfterSuite(alwaysRun = true)
    public void destroy() throws Exception {
        balServer.cleanup();
        jaegerServer.cleanUp();
    }
}