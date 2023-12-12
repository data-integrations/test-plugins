/*
 * Copyright © 2023 Cask Data, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package io.cdap.plugin.testplugins.stepsdesign;

import io.cdap.e2e.utils.CdfHelper;
import io.cdap.plugin.testplugins.actions.EnvTesterTransformActions;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;

/**
 * Env Tester Transform Plugin related step design.
 */
public class EnvTesterTransform implements CdfHelper {
    @And("Enter Environment variables with value: {string}")
    public void enterEnvironmentVariablesWithValue(String value) {
        EnvTesterTransformActions.enterEnvironmentVariable(value);
    }

    @And("set runtime argument with value {string} and key {string}")
    public void setRuntimeArgumentWithValueAndKey(String value, String key) {
        EnvTesterTransformActions.setRunTimeArguments(key, value);
    }

    @Then("Run the Pipeline with runtime arguments")
    public void runThePipelineWithRuntimeArguments() {
        EnvTesterTransformActions.saveTheRuntimeArgsAndRunThePipeline();
    }
}
