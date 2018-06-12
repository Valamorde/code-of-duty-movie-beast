## This is simply a structural placeholder of the master branch, nothing to do yet.

## How to use Git (Just in case someone forgot)

a) Navigate to the directory you want to work in. <br />
b) Clone the repository by running "git clone https://Valamorde@bitbucket.org/Valamorde/cod-ticket-monster.git" (without quotes in CMD, PowerShell or GitBash etc.)<br />
c) Open the new folder (cod-ticket-monster) in explorer or CD through CMD, PowerShell, GitBash<br />
d) Run "git checkout -b [name_of_your_new_branch]"<br />
e) Do what you need to do <br />
f) When done run "git status", "git add [name_of_the_file_you_want_to_stage_or_period_for_all]", "git commit -m '[your_message_here_KISS]'", "git push origin [name_of_your_new_branch]" <br />
g) You're done. <br />

## Important

a) We are following the Git Workflow (master, develop, release, feature(s)) <br />
b) On a daily basis before doing anything run "git fetch origin [name_of_your_branch]" followed by "git pull origin [name_of_your_branch]" to make sure you re not having any conflicts <br />
c) At the end of the day always commit and push your changes. <br />

## Having questions about Git? Check this out: <br />
https://github.com/Kunena/Kunena-Forum/wiki/Create-a-new-branch-with-git-and-manage-branches <br />