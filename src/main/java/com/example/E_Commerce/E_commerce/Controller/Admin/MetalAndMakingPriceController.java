package com.example.E_Commerce.E_commerce.Controller.Admin;
import com.example.E_Commerce.E_commerce.Models.Admin.request.SheetDocument;
import com.example.E_Commerce.E_commerce.Models.Auth.response.RestResponseStatus;
import com.example.E_Commerce.E_commerce.Service.admin.MetalAndMakingPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class MetalAndMakingPriceController {
    @Autowired
    MetalAndMakingPriceService metalAndMakingPriceService;

    @RequestMapping(value = "admin/addMetalAndMakingPriceSheet", method = RequestMethod.POST,consumes = {"multipart/form-data"})
    public ResponseEntity<?> addDiamondChartAndPrices(@ModelAttribute SheetDocument sheetDocument) throws Exception{
        try{
            return ResponseEntity.ok(
                    new RestResponseStatus(
                            metalAndMakingPriceService.saveMetalAndMakingPrice(sheetDocument)?
                                    "Added Metal Prices and Making charges successfully from the Sheet!"
                                    :"Couldn't add Metal Prices and Making charges from the Sheet!"
                    )
            );
        } catch (Exception e){
            return ResponseEntity.ok(new RestResponseStatus("Couldn't add Metal Prices and Making charges from the Sheet!"));
        }
    }
}
