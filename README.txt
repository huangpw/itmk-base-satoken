【Gitee】
mkdir itmk-base-satoken
cd itmk-base-satoken
git init 
touch README.md
git add README.md
git commit -m "first commit"
git remote add origin https://gitee.com/huangpeiwen/itmk-base-satoken.git
git push -u origin "master"

【Github】
echo "# itmk-base-satoken" >> README.md
git init
git add README.md
git commit -m "first commit"
git branch -M master
git remote add origin https://github.com/huangpw/itmk-base-satoken.git
git push -u origin master

【删除现有的 origin 并重新设置】
git remote remove origin
git remote add origin 新的仓库URL
