kotlin_sources := '**/src/**/*.kt*'

default:
    @just --list

lint:
    @just --unstable --fmt --check
    @ktlint {{ kotlin_sources }}

format:
    @just --unstable --fmt
    @ktlint --format {{ kotlin_sources }}

build:
    ./gradlew :composeApp:assemble

test:
    ./gradlew :composeApp:connectedAndroidTest --info
