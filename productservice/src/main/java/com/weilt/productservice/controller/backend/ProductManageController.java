package com.weilt.productservice.controller.backend;

import com.google.common.collect.Maps;
import com.weilt.commonentity.commonentity.Const;
import com.weilt.commonentity.commonentity.ResponseCode;
import com.weilt.commonentity.commonentity.ServerResponse;
import com.weilt.commonentity.entity.Product;
import com.weilt.commonentity.entity.User;
import com.weilt.commonentity.service.IFileService;
import com.weilt.productservice.service.IProductService;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.PropertiesUtil;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author weilt
 * @com.weilt.eshopproduct.controller.backend
 * @date 2018/8/23 == 12:12
 */
@RestController
@RequestMapping(value = "/manage/product")
public class ProductManageController {

    @Autowired
    private IProductService iProductService;
    @Autowired
    private IFileService iFileService;


    @PostMapping(value = "/saveproduct")
    public ServerResponse productSave(HttpSession session, Product product) {
        User user =(User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),Const.User.NEED_LOGIN);
        }
        //校验是否管理员
        if(user.getRole() == Const.Role.ROLE_ADMIN){
            //是管理员
            return iProductService.saveOrUpdateProduct(product);
        }
        else {
            return ServerResponse.createByErrorMessage(Const.User.NOT_MANAGER);
        }
    }

    @PostMapping(value = "/setproductstatus")
    public ServerResponse setProductStatus(HttpSession session,Integer productId,Integer status) {
        User user =(User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),Const.User.NEED_LOGIN);
        }
        //校验是否管理员
        if(user.getRole() == Const.Role.ROLE_ADMIN){
            //是管理员
            return iProductService.setProductStatus(productId,status);
        }
        else {
            return ServerResponse.createByErrorMessage(Const.User.NOT_MANAGER);
        }
    }

    @PostMapping(value = "/getproductlist")
    public ServerResponse getProductList(HttpSession session, @RequestParam(value = "pageNum",defaultValue = "1") int pageNum,@RequestParam(value = "pageSize",defaultValue = "10") int pageSize){
        User user =(User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),Const.User.NEED_LOGIN);
        }
        //校验是否管理员
        if(user.getRole() == Const.Role.ROLE_ADMIN){
            //是管理员
            return iProductService.getProductList(pageNum,pageSize);
        }
        else {
            return ServerResponse.createByErrorMessage(Const.User.NOT_MANAGER);
        }
    }

    @PostMapping(value = "/serarch")
    public ServerResponse serarchProduct(HttpSession session, String productName,Integer productId,@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,@RequestParam(value = "pageSize",defaultValue = "10") int pageSize){
        User user =(User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),Const.User.NEED_LOGIN);
        }
        //校验是否管理员
        if(user.getRole() == Const.Role.ROLE_ADMIN){
            //是管理员
            return iProductService.secrchProduct(productName,productId,pageNum,pageSize);
        }
        else {
            return ServerResponse.createByErrorMessage(Const.User.NOT_MANAGER);
        }
    }

    @PostMapping(value = "/uploadimg")
    public ServerResponse upload(HttpSession session,@RequestParam(value = "upload_file",required = false) MultipartFile file, HttpServletRequest request){

        User user =(User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),Const.User.NEED_LOGIN);
        }
        //校验是否管理员
        if(user.getRole() == Const.Role.ROLE_ADMIN){
            //是管理员
            String path = request.getSession().getServletContext().getRealPath("upload");
            String targetFileName = iFileService.upload(file,path);
            String url = PropertiesUtil.getProperties().getStringProperty("ftp.server.http.prefix")+targetFileName;
            Map fileMap = Maps.newHashMap();
            fileMap.put("uri",targetFileName);
            fileMap.put("url",url);
            return ServerResponse.createBySuccessData(fileMap);
        }
        else {
            return ServerResponse.createByErrorMessage(Const.User.NOT_MANAGER);
        }
    }

    @PostMapping("/richtext_img_upload")
    public Map richtextImgUpload(HttpSession session, @RequestParam(value = "upload_file",required = false) MultipartFile file, HttpServletRequest request, HttpServletResponse response){
        Map resultMap = Maps.newHashMap();
        User user =(User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            resultMap.put("success",false);
            resultMap.put("msg",Const.User.NOT_MANAGER);
            return resultMap;
        }
        //校验是否管理员
        if(user.getRole() == Const.Role.ROLE_ADMIN){
            //是管理员
            String path = request.getSession().getServletContext().getRealPath("upload");
            String targetFileName = iFileService.upload(file,path);
            if(StringUtils.isBlank(targetFileName)){

                resultMap.put("success",false);
                resultMap.put("msg","上传失败");
                return resultMap;
            }
            String url = PropertiesUtil.getProperties().getStringProperty("ftp.server.http.prefix")+targetFileName;
            resultMap.put("success",true);
            resultMap.put("msg","上传成功");
            resultMap.put("file_path",url);
            response.addHeader("Access-Control-Allow-Headers","X-File-Name");
            return resultMap;
        }
        else {
            resultMap.put("success",false);
            resultMap.put("msg",Const.User.NOT_MANAGER);
            return resultMap;
        }
    }
}
