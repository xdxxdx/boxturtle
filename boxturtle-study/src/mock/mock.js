import Mock from 'mockjs';

// 配置拦截 ajax 的请求时的行为，支持的配置项目有 timeout。
 Mock.setup({
    timeout: '200 - 400'
})

const vCode = '123456';
const tags=[{
    tagName:'java基础',
    priority:1
},{
    tagName:'vue基础',
    priority:2
},{
    tagName:'spring boot',
    priority:3
},{
    tagName:'nginx学习',
    priority:4
},{
    tagName:'经验',
    priority:1
},{
    tagName:'redis',
    priority:6
},];

function getVerificatCode (prarms) {
    const prarmsObj = JSON.parse(prarms.body);
    return Object.assign(prarmsObj, {vCode: vCode});
}

function loginFun (prarms) {
    const prarmsObj = JSON.parse(prarms.body);
    if (prarmsObj.code === vCode) {
        return {code:1, text:'登录成功'}
    } else {
        return {code:2, text:'验证码有误，登录失败'}  
    }
}
function tagAdd (params){
    const prarmsObj = JSON.parse(params.body);
    if(prarmsObj.tagName!=null&&prarmsObj.priority!=null){
        tags.push({tagName:prarmsObj.tagName,priority:prarmsObj.priority})
        return {msg:'添加成功'}
    }else{
        return {msg:'添加失败'};
    }
}
function tagEdit (params){
    const prarmsObj = JSON.parse(params.body);
    for(let i in tags){
        console.log(tags[i]);
        if(tags[i].tagName==prarmsObj.tagName){
            tags[i].priority=prarmsObj.priority;
        }
    }
    return {msg:'修改成功'};
}
function tagList(){
    return tags
}
function tagRemove(params){
    const prarmsObj = JSON.parse(params.body);
    console.log(prarmsObj.tagName);
    for(let i in tags){
        console.log(tags[i]);
        if(tags[i].tagName==prarmsObj.tagName){
            tags.splice(i,1);
        }
    }
    return {msg:'删除成功'};
}
function tagDetail(params){
    const prarmsObj = JSON.parse(params.body);
    console.log(prarmsObj);
    for(let i in tags){
        if(tags[i].tagName==prarmsObj.tagName){
            return tags[i];
        }
    }
    return null;
}
 
// Mock.mock( url, post/get , 返回的数据)；
Mock.mock('/getVerificatCode', 'post', getVerificatCode); // 获取验证码
Mock.mock('/login', 'post', loginFun); //登录
Mock.mock('/tag/add', 'post', tagAdd); //添加标签
Mock.mock('/tag/list', 'get', tagList); //标签列表
Mock.mock('/tag/remove', 'delete', tagRemove); //标签列表
Mock.mock('/tag/detail', 'get', tagDetail); //标签详情
Mock.mock('/tag/edit', 'post', tagEdit); //标签修改