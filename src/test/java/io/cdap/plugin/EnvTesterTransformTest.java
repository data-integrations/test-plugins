/*
 * Copyright © 2017 Cask Data, Inc.
 *
:49
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
package io.cdap.plugin;

import io.cdap.cdap.api.data.format.StructuredRecord;
import io.cdap.cdap.etl.api.Transform;
import org.junit.Test;

import static com.github.stefanbirkner.systemlambda.SystemLambda.withEnvironmentVariable;

/**
 * Test for EnvTester transform.
 */
public class EnvTesterTransformTest {
  @Test
  public void testInitialize() throws Exception {
    EnvTesterTransformPlugin.Config config = new EnvTesterTransformPlugin.Config("-Dfoo=bar,-Da=b");
    Transform<StructuredRecord, StructuredRecord> transform = new EnvTesterTransformPlugin(config);
    withEnvironmentVariable("SPARK_OPT_1", "-Dfoo=bar").and("SPARK_OPT_2", "-Da=b")
      .execute(() -> {
      transform.initialize(null);
    });
  }

  @Test
  public void testPrepareRun() throws Exception {
    EnvTesterTransformPlugin.Config config = new EnvTesterTransformPlugin.Config("-Dfoo=bar,-Da=b");
    Transform<StructuredRecord, StructuredRecord> transform = new EnvTesterTransformPlugin(config);
    withEnvironmentVariable("OPTS", "-Dmykey=myval -Dfoo=bar -Dx=y -Da=b")
      .execute(() -> {
        transform.prepareRun(null);
      });
  }
}
