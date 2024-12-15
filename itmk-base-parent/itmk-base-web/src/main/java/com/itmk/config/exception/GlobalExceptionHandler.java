package com.itmk.config.exception;

import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.exception.NotRoleException;
import com.itmk.utils.ResultUtils;
import com.itmk.utils.ResultVo;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.aspectj.weaver.ast.Not;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: AlbertHPW
 * @Date: 2024/12/15 17:18
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 自定义业务异常拦截器
     * BusinessException
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    @RequestBody
    public ResultVo businessException(BusinessException e){
        System.out.println("\033[31m"+e.getMessage()+"\033[0m");
        return ResultUtils.error(e.getMessage(), e.getCode(), e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResultVo handlerException(Exception e, HttpServletRequest request, HttpServletResponse response) throws Exception {
        int code = 500;
        String str = "";
        // 打印堆栈信息，以供测试
        System.out.println("全局异常------------");
        e.printStackTrace();

        // 无角色访问异常
        if(e instanceof NotRoleException) {
            NotRoleException ee = (NotRoleException) e;
            str = ("无此角色" + ee.getRole());
        }
        // 无权限访问的异常
        else if(e instanceof NotPermissionException) {
            // 如果是权限异常
            str = "无权限操作该功能！";
        } else {
            // 普通异常，输出：500 + 异常信息
            str = e.getMessage();
        }
        // 返回给前端
        return ResultUtils.error(str, code, e.getMessage());
    }
}
