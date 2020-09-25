rootProject.name = "demo-sender"

pluginManagement {
    val kotlinVersion: String by settings
    val springCloudContractVersion: String by settings

    plugins {
        kotlin("jvm") version kotlinVersion
        kotlin("kapt") version kotlinVersion
        kotlin("plugin.spring") version kotlinVersion

        id("org.springframework.cloud.contract") version springCloudContractVersion
    }
}
