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

package io.cdap.plugin.testplugins.common.stepsdesign;


import com.google.cloud.bigquery.BigQueryException;
import io.cdap.e2e.utils.BigQueryClient;
import io.cdap.e2e.utils.PluginPropertyUtils;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;

import org.junit.Assert;
import stepsdesign.BeforeActions;

import java.io.IOException;

/**
 * EnvTesterTransform hooks.
 */
public class TestSetupHooks {
  private static String bqSourceTable = StringUtils.EMPTY;
  private static String bqTargetTable = StringUtils.EMPTY;

  @Before(order = 1, value = "@BQ_SOURCE")
  public static void setTempSourceBQTable() throws IOException, InterruptedException {
    String bqDataset = PluginPropertyUtils.pluginProp("dataset");
    String stringUniqueId = "TestID-" + RandomStringUtils.randomAlphanumeric(5);
    bqSourceTable = "TestEnvTransformTable_" + RandomStringUtils.randomAlphanumeric(10);

    BigQueryClient.getQueryResult("create table `" + bqDataset + "." + bqSourceTable + "` as " +
            "SELECT * FROM UNNEST([ STRUCT('" + stringUniqueId + "' " +
            "AS UniqueId, '2023-23-11 00:00:00'  AS creationTime)])");


    PluginPropertyUtils.addPluginProp("bqSourceTable", bqSourceTable);
    BeforeActions.scenario.write("BigQuery Source table name: " + bqSourceTable);
  }

  @After(order = 1, value = "@BQ_SOURCE_CLEANUP")
  public static void deleteTempSourceBQTable() throws IOException, InterruptedException {
    try {
      BigQueryClient.dropBqQuery(bqSourceTable);
      BeforeActions.scenario.write("BigQuery Target table: " + bqSourceTable + " is deleted successfully");
      bqSourceTable = StringUtils.EMPTY;
    } catch (BigQueryException e) {
      if (e.getCode() == 404) {
        BeforeActions.scenario.write("BigQuery Target Table: " + bqSourceTable + " does not exist");
      } else {
        Assert.fail(e.getMessage());
      }
    }
  }

  @Before(order = 2, value = "@BQ_SINK")
  public static void setTempTargetBQTable() {
    bqTargetTable = "TestSN_table" + RandomStringUtils.randomAlphanumeric(10);
    PluginPropertyUtils.addPluginProp("bqSinkTable", bqTargetTable);
    BeforeActions.scenario.write("BigQuery Target table name: " + bqTargetTable);
  }

  @After(order = 2, value = "@BQ_SINK_CLEANUP")
  public static void deleteTempTargetBQTable() throws IOException, InterruptedException {
    try {
      BigQueryClient.dropBqQuery(bqTargetTable);
      BeforeActions.scenario.write("BigQuery Target table: " + bqTargetTable + " is deleted successfully");
      bqTargetTable = StringUtils.EMPTY;
    } catch (BigQueryException e) {
      if (e.getCode() == 404) {
        BeforeActions.scenario.write("BigQuery Target Table: " + bqTargetTable + " does not exist");
      } else {
        Assert.fail(e.getMessage());
      }
    }
  }
}
