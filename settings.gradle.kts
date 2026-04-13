pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Dahuka_DevMobileApp"
include(":app")
include(":app:xem_san_pham")
include(":app:dang_nhap")
include(":app:dang_ky")
include(":app:gio_hang")
include(":app:quen_mk")
include(":app:ql_don_hang")
include(":app:so_dia_chi")
include(":app:quen_mk2")
include(":app:quan_ly_thong_tin_ca_nhan")
