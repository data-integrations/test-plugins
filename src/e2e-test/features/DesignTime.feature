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

Feature: Env Tester Transform - Design time scenarios

  @ENV @BQ_SOURCE @BQ_SOURCE_CLEANUP
  Scenario:Verify if user is able to successfully validate the plugin.
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
