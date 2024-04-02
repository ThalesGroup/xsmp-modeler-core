cmake_minimum_required(VERSION 3.14)
project(
    xsmp_sdk_tests
#   VERSION 1.0.0
#   DESCRIPTION ""
#   HOMEPAGE_URL ""
    LANGUAGES CXX)



include(FetchContent)
FetchContent_Declare(
  xsmp-sdk
  GIT_REPOSITORY https://github.com/ThalesGroup/xsmp-sdk.git
  GIT_TAG        main 
)

FetchContent_MakeAvailable(xsmp-sdk)


file(GLOB_RECURSE SRC CONFIGURE_DEPENDS src/*.cpp src-gen/*.cpp)

add_library(xsmp_sdk_tests SHARED ${SRC})
target_include_directories(xsmp_sdk_tests PUBLIC src src-gen)
target_link_libraries(xsmp_sdk_tests PUBLIC Xsmp::Cdk)


# --------------------------------------------------------------------

if(CMAKE_PROJECT_NAME STREQUAL PROJECT_NAME)
    include(CTest)
    FetchContent_Declare(
        googletest
        GIT_REPOSITORY https://github.com/google/googletest.git
        GIT_TAG        v1.14.0
    )
    # For Windows: Prevent overriding the parent project's compiler/linker settings
    set(gtest_force_shared_crt ON CACHE BOOL "" FORCE)
    set(BUILD_GMOCK OFF CACHE BOOL "")
    set(INSTALL_GTEST OFF CACHE BOOL "")
    FetchContent_MakeAvailable(googletest)

    include(GoogleTest)
    file(GLOB_RECURSE SRC-TEST CONFIGURE_DEPENDS tests/*.cpp)
    add_executable (xsmp_sdk_profile_tests ${SRC-TEST})

    target_include_directories(xsmp_sdk_profile_tests PRIVATE tests)
    target_link_libraries(
        xsmp_sdk_profile_tests PUBLIC
        xsmp_sdk_tests
        GTest::gtest_main
    )

    gtest_discover_tests(xsmp_sdk_profile_tests)
endif()


