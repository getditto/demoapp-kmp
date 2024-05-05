default:
    @just --list

lint:
    @just --unstable --fmt --check

format:
    @just --unstable --fmt
