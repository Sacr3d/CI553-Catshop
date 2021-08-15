# 2020-CI553-catshop
CatShop system for 2020-CI553 (2020-21 cohort)

Initialised from 2019-CI553- catshop Version 1.2.1

![Java CI/CD Pipeline with Maven](https://github.com/Sacr3d/CI553-Catshop/actions/workflows/maven.yml/badge.svg)

# Prerequisites
Maven

Git Bash (If on Windows)

# Info
Make script will not work in Windows Powershell or CMD

# Using Make
`make` cleans all previous builds and creates installs the catshop program locally with dependancies

`make package` cleans all previous builds and packages catshop for testing

`make clean-all` cleans all previous builds

`make clean-local` cleans only the local install

`make clean-lib` removes the libraries from the local install

`make doc` generates java doc in target folder

`make jarrun` runs catshop local install

`make test` runs Junit Tests with Maven

`make distributed` creates the distributed environment for testing

`make database` creates the Derby database

`make kill-all` a crude solution to killing **ALL JAVA PROCCESSES** if components of catshop didn't close properly
