/*
 * Copyright © 2017 Cask Data, Inc.
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

package io.cdap.plugin;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import io.cdap.cdap.api.annotation.Description;
import io.cdap.cdap.api.annotation.Macro;
import io.cdap.cdap.api.annotation.Name;
import io.cdap.cdap.api.annotation.Plugin;
import io.cdap.cdap.api.data.format.StructuredRecord;
import io.cdap.cdap.api.data.schema.Schema;
import io.cdap.cdap.api.plugin.PluginConfig;
import io.cdap.cdap.etl.api.Emitter;
import io.cdap.cdap.etl.api.PipelineConfigurer;
import io.cdap.cdap.etl.api.StageSubmitterContext;
import io.cdap.cdap.etl.api.Transform;
import io.cdap.cdap.etl.api.TransformContext;
import java.util.Collections;
import java.util.List;

/**
 * Plugin checks if the specified environment variables are set and fails the pipeline if they are not set.
 */
@Plugin(type = Transform.PLUGIN_TYPE)
@Name("EnvTesterTransform")
@Description("Environment variable tester.")
public class EnvTesterTransformPlugin extends Transform<StructuredRecord, StructuredRecord> {
  private final Config config;

  public EnvTesterTransformPlugin(Config config) {
    this.config = config;
  }

  @Override
  public void configurePipeline(PipelineConfigurer pipelineConfigurer) throws IllegalArgumentException {
    super.configurePipeline(pipelineConfigurer);
    Schema inputSchema = pipelineConfigurer.getStageConfigurer().getInputSchema();
    pipelineConfigurer.getStageConfigurer().setOutputSchema(inputSchema);
  }

  @Override
  public void initialize(TransformContext context) throws Exception {
    super.initialize(context);
    List<String> envVars = Strings.isNullOrEmpty(config.envVars) ? Collections.emptyList() :
      Splitter.on(",").splitToList(config.envVars);
    for (String envVar: envVars) {
      if (!System.getenv().containsValue(envVar)) {
        throw new Exception(String.format("Environment variable %s is not set", envVar));
      }
    }
  }

  @Override
  public void prepareRun(StageSubmitterContext context) throws Exception {
    super.prepareRun(context);
    List<String> envVars = Strings.isNullOrEmpty(config.envVars) ? Collections.emptyList() :
      Splitter.on(",").splitToList(config.envVars);
    for (String envVar: envVars) {
      if (!System.getenv().containsKey("OPTS") || !System.getenv().get("OPTS").contains(envVar)) {
        throw new Exception(String.format("Environment variable %s is not set", envVar));
      }
    }
  }

  @Override
  public void transform(StructuredRecord input, Emitter<StructuredRecord> emitter) {
    Schema outputSchema = input.getSchema();
    List<Schema.Field> fields = outputSchema.getFields();
    StructuredRecord.Builder builder = StructuredRecord.builder(outputSchema);
    for (Schema.Field field : fields) {
      String name = field.getName();
      if (input.get(name) != null) {
        builder.set(name, input.get(name));
      }
    }
    emitter.emit(builder.build());
  }

  /**
   * EnvTester plugin configuration.
   */
  public static class Config extends PluginConfig {
    @Name("envVars")
    @Description("Environment variables that should be set.")
    @Macro
    private final String envVars;

    public Config(String envVars) {
      this.envVars = envVars;
    }
  }
}

