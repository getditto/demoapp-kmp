kotlin_sources := '**/src/**/*.kt*'

default:
    @just --list

tools:
    @./gradlew --version
    @xcodebuild -version

plugins:
    @./gradlew buildEnvironment --quiet | grep '^\+'

targets:
    xcodebuild \
        -workspace iosApp/iosApp.xcworkspace \
        -scheme 'Ditto KMP' \
        -list

destinations:
    xcodebuild \
        -workspace iosApp/iosApp.xcworkspace \
        -scheme 'Ditto KMP' \
        -showdestinations

lint:
    @just --unstable --fmt --check
    @ktlint {{ kotlin_sources }}

format:
    @just --unstable --fmt
    @ktlint --format {{ kotlin_sources }}

clean:
    ./gradlew :clean

install:
    pod install \
        --project-directory=iosApp/
    ./gradlew :composeApp:podInstallSyntheticIos

build:
    ./gradlew :composeApp:assemble

    pod install \
        --project-directory=iosApp/

    set -o pipefail && \
    xcodebuild \
        -workspace iosApp/iosApp.xcworkspace \
        -scheme 'Ditto KMP' \
        -configuration Debug \
        -destination 'generic/platform=iOS Simulator' | xcbeautify

test:
    ./gradlew :composeApp:connectedAndroidTest --info
