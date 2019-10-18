import Mock from 'mockjs';

// 配置拦截 ajax 的请求时的行为，支持的配置项目有 timeout。
 Mock.setup({
    timeout: '200 - 400'
})

const vCode = '123456';

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
        return {msg:'添加成功'}
    }else{
        return {msg:'添加失败'};
    }
}
 
// Mock.mock( url, post/get , 返回的数据)；
Mock.mock('/getVerificatCode', 'post', getVerificatCode); // 获取验证码
Mock.mock('/login', 'post', loginFun); //登录
Mock.mock('/tag/add', 'post', tagAdd); //添加标签