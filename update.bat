@echo off

git remote update

git fetch major

git rebase major/master