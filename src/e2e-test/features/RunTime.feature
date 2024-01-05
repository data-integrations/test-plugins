#
# Copyright © 2023 Cask Data, Inc.
#
# Licensed under the Apache License, Version 2.0 (the "License"); you may not
# use this file except in compliance with the License. You may obtain a copy of
# the License at
#
# http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
# WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
# License for the specific language governing permissions and limitations under
# the License.
#

Feature: Env Tester Transform - Run time scenarios

  @ENV @BQ_SOURCE @BQ_SOURCE_CLEANUP @BQ_SINK @BQ_SINK_CLEANUP
  Scenario: Verify if user is able to successfully deploy and run a pipeline with single environment variable.
    Given Open Datafusion Project to configure pipeline
    And Select plugin: "BigQuery" from the plugins list as: "Source"
    And Navigate to the properties page of plugin: "BigQuery"
    And Enter input plugin property: "referenceName" with value: "ReferenceName"
    And Replace input plugin property: "project" with value: "projectId"
    And Enter input plugin property: "datasetProject" with value: "datasetProjectId"
    And Enter input plugin property: "dataset" with value: "dataset"
    And Enter input plugin property: "table" with value: "bqSourceTable"
    And Validate "BigQuery" plugin properties
    And Close the Plugin Properties page
    When Expand Plugin group in the LHS plugins list: "Transform"
    Then Select plugin: "Env Tester Transform" from the plugins list as: "Transform"
    And Connect plugins: "BigQuery" and "EnvTesterTransform" to establish connection
    And Navigate to the properties page of plugin: "EnvTesterTransform"
    And Enter Environment variables with value: "validProxyPort"
    And Validate "Env Tester Transform" plugin properties
    And Close the Plugin Properties page
    When Expand Plugin group in the LHS plugins list: "Sink"
    When Select plugin: "BigQuery" from the plugins list as: "Sink"
    And Connect plugins: "EnvTesterTransform" and "BigQuery2" to establish connection
    And Navigate to the properties page of plugin: "BigQuery2"
    And Enter input plugin property: "referenceName" with value: "ReferenceName"
    And Replace input plugin property: "project" with value: "projectId"
    And Enter input plugin property: "datasetProject" with value: "datasetProjectId"
    And Enter input plugin property: "dataset" with value: "dataset"
    And Enter input plugin property: "table" with value: "bqSinkTable"
    And Validate "BigQuery2" plugin properties
    And Close the Plugin Properties page
    And Save the pipeline
    And Deploy the pipeline
    And set runtime argument with value "validProxyPort" and key "jvmOptions"
    And Wait till pipeline is in running state
    And Open and capture logs
    Then Verify the pipeline status is "Succeeded"

  @ENV @BQ_SOURCE @BQ_SOURCE_CLEANUP @BQ_SINK @BQ_SINK_CLEANUP
  Scenario: Verify if user is able to successfully deploy and run a pipeline with multiple environment variables.
    Given Open Datafusion Project to configure pipeline
    And Select plugin: "BigQuery" from the plugins list as: "Source"
    And Navigate to the properties page of plugin: "BigQuery"
    And Enter input plugin property: "referenceName" with value: "ReferenceName"
    And Replace input plugin property: "project" with value: "projectId"
    And Enter input plugin property: "datasetProject" with value: "datasetProjectId"
    And Enter input plugin property: "dataset" with value: "dataset"
    And Enter input plugin property: "table" with value: "bqSourceTable"
    And Validate "BigQuery" plugin properties
    And Close the Plugin Properties page
    When Expand Plugin group in the LHS plugins list: "Transform"
    Then Select plugin: "Env Tester Transform" from the plugins list as: "Transform"
    And Connect plugins: "BigQuery" and "EnvTesterTransform" to establish connection
    And Navigate to the properties page of plugin: "EnvTesterTransform"
    And Enter Environment variables with value: "validEnvVars"
    And Validate "Env Tester Transform" plugin properties
    And Close the Plugin Properties page
    When Expand Plugin group in the LHS plugins list: "Sink"
    When Select plugin: "BigQuery" from the plugins list as: "Sink"
    And Connect plugins: "EnvTesterTransform" and "BigQuery2" to establish connection
    And Navigate to the properties page of plugin: "BigQuery2"
    And Enter input plugin property: "referenceName" with value: "ReferenceName"
    And Replace input plugin property: "project" with value: "projectId"
    And Enter input plugin property: "datasetProject" with value: "datasetProjectId"
    And Enter input plugin property: "dataset" with value: "dataset"
    And Enter input plugin property: "table" with value: "bqSinkTable"
    And Validate "BigQuery2" plugin properties
    And Close the Plugin Properties page
    And Save the pipeline
    And Deploy the pipeline
    And set runtime argument with value "multipleEnvVars" and key "jvmOptions"
    And Wait till pipeline is in running state
    And Open and capture logs
    Then Verify the pipeline status is "Succeeded"

  @ENV @BQ_SOURCE @BQ_SOURCE_CLEANUP @BQ_SINK @BQ_SINK_CLEANUP
  Scenario: Verify if pipeline is fails when user check for invalid environment variables.
    Given Open Datafusion Project to configure pipeline
    And Select plugin: "BigQuery" from the plugins list as: "Source"
    And Navigate to the properties page of plugin: "BigQuery"
    And Enter input plugin property: "referenceName" with value: "ReferenceName"
    And Replace input plugin property: "project" with value: "projectId"
    And Enter input plugin property: "datasetProject" with value: "datasetProjectId"
    And Enter input plugin property: "dataset" with value: "dataset"
    And Enter input plugin property: "table" with value: "bqSourceTable"
    And Validate "BigQuery" plugin properties
    And Close the Plugin Properties page
    When Expand Plugin group in the LHS plugins list: "Transform"
    Then Select plugin: "Env Tester Transform" from the plugins list as: "Transform"
    And Connect plugins: "BigQuery" and "EnvTesterTransform" to establish connection
    And Navigate to the properties page of plugin: "EnvTesterTransform"
    And Enter Environment variables with value: "validEnvVars"
    And Validate "Env Tester Transform" plugin properties
    And Close the Plugin Properties page
    When Expand Plugin group in the LHS plugins list: "Sink"
    When Select plugin: "BigQuery" from the plugins list as: "Sink"
    And Connect plugins: "EnvTesterTransform" and "BigQuery2" to establish connection
    And Navigate to the properties page of plugin: "BigQuery2"
    And Enter input plugin property: "referenceName" with value: "ReferenceName"
    And Replace input plugin property: "project" with value: "projectId"
    And Enter input plugin property: "datasetProject" with value: "datasetProjectId"
    And Enter input plugin property: "dataset" with value: "dataset"
    And Enter input plugin property: "table" with value: "bqSinkTable"
    And Validate "BigQuery2" plugin properties
    And Close the Plugin Properties page
    And Save the pipeline
    And Deploy the pipeline
    And set runtime argument with value "invalidMultipleEnvVars" and key "jvmOptions"
    And Wait till pipeline is in running state
    And Open and capture logs
    Then Verify the pipeline status is "Failed"
