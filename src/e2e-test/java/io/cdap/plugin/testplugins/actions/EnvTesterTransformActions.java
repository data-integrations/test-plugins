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

package io.cdap.plugin.testplugins.actions;


import io.cdap.e2e.pages.actions.CdfPipelineRunAction;
import io.cdap.e2e.pages.locators.CdfPipelineRunLocators;
import io.cdap.e2e.utils.ElementHelper;
import io.cdap.e2e.utils.PluginPropertyUtils;
import io.cdap.plugin.testplugins.locators.EnvTesterTransformLocators;

/**
 * EnvTesterTransform Actions.
 */
public class EnvTesterTransformActions {

    public static void enterEnvironmentVariable(String value) {
        String[] valueFromPluginPropertiesFile = PluginPropertyUtils.pluginProp(value).split(",");

        for (int index = 0; index < valueFromPluginPropertiesFile.length; index++) {
            if (valueFromPluginPropertiesFile == null) {
                ElementHelper.sendKeys(EnvTesterTransformLocators.envVariablesInputBox(index), value);
                ElementHelper.clickOnElement(EnvTesterTransformLocators.clickEnvVarsAddRowButton(index));
            } else {
                ElementHelper.sendKeys(EnvTesterTransformLocators.envVariablesInputBox(index),
                  valueFromPluginPropertiesFile[index]);
                ElementHelper.clickOnElement(EnvTesterTransformLocators.clickEnvVarsAddRowButton(index));
            }
        }
    }

    public static void setRunTimeArguments(String key, String value) {
        String keyFromPluginPropertiesFile = PluginPropertyUtils.pluginProp(key);
        String valueFromPluginPropertiesFile = PluginPropertyUtils.pluginProp(value);

        ElementHelper.clickOnElement(EnvTesterTransformLocators.configureRunTimeArgs());
        ElementHelper.sendKeys(EnvTesterTransformLocators.runTimeArgumentsKeyInputBox(), keyFromPluginPropertiesFile);
        ElementHelper.sendKeys(EnvTesterTransformLocators.runTimeArgumentsValueInputBox(),
          valueFromPluginPropertiesFile);
        ElementHelper.clickOnElement(EnvTesterTransformLocators.save());
        CdfPipelineRunAction.runClick();
    }

    public static void saveTheRuntimeArgsAndRunThePipeline() {
        ElementHelper.clickOnElement(EnvTesterTransformLocators.save());
        ElementHelper.clickOnElement(CdfPipelineRunLocators.deployedConfigRunButton);
    }
}
