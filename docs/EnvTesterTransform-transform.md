# EnvTester Transform

Transform plugin that checks if specified environment variables are set when the pipeline runs, and fails the pipeline if they are not not set.

**NOTE**: This plugin only works when running pipelines on Kubernetes. It does *not* work in preview mode.