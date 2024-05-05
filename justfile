default:
    @just --list

lint:
    @just --unstable --fmt --check
    @ktlint

format:
    @just --unstable --fmt
    @ktlint --format
