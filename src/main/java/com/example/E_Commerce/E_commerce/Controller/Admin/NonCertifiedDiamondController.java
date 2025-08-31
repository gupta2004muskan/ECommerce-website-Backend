package com.example.E_Commerce.E_commerce.Controller.Admin;

import com.example.E_Commerce.E_commerce.Models.Admin.entities.models.ClarityAndColor;
import com.example.E_Commerce.E_commerce.Models.Admin.entities.models.NonCertDiamondName;
import com.example.E_Commerce.E_commerce.Models.Admin.request.DiamondSpecsRequest;
import com.example.E_Commerce.E_commerce.Models.Admin.request.ListOfDiamondSpecsRequest;
import com.example.E_Commerce.E_commerce.Models.Admin.request.SheetDocument;
import com.example.E_Commerce.E_commerce.Models.Admin.response.DiamondSpecsResponse;
import com.example.E_Commerce.E_commerce.Models.Admin.response.ListOfDiamondSpecsResponse;
import com.example.E_Commerce.E_commerce.Models.Auth.response.RestResponseStatus;
import com.example.E_Commerce.E_commerce.Service.admin.NonCertifiedDiamondService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class NonCertifiedDiamondController {
    @Autowired
    private NonCertifiedDiamondService nonCertifiedDiamondService;

    @RequestMapping(value = "admin/addDiamondChartAndPrices", method = RequestMethod.POST,consumes = {"multipart/form-data"})
    public ResponseEntity<?> addDiamondChartAndPrices(@ModelAttribute SheetDocument sheetDocument, @RequestParam boolean onlyPricing) throws Exception{
        nonCertifiedDiamondService.addDiamondPrices(sheetDocument,onlyPricing);
        if(onlyPricing)
            return ResponseEntity.ok(new RestResponseStatus("Added Diamond Prices from the Sheet Successfully"));
        return ResponseEntity.ok(new RestResponseStatus("Added Diamonds Sieve Size, Weight and Prices from the Sheet Successfully"));
    }

    @RequestMapping(value = "admin/addDiamondChart", method = RequestMethod.POST,consumes = {"multipart/form-data"})
    public ResponseEntity<?> addDiamondChart(@ModelAttribute SheetDocument sheetDocument) throws Exception{
        nonCertifiedDiamondService.addOnlyDiamondChart(sheetDocument);
        return ResponseEntity.ok(new RestResponseStatus("Added Diamonds Sieve Size, Weight from the Sheet Successfully"));
    }

    @RequestMapping(value = "admin/addDiamondPrices", method = RequestMethod.POST,consumes = {"multipart/form-data"})
    public ResponseEntity<?> addDiamondPrices(@ModelAttribute SheetDocument sheetDocument) throws Exception{
        nonCertifiedDiamondService.addDiamondPrices(sheetDocument,true);
        return ResponseEntity.ok(new RestResponseStatus("Added Diamond Prices from the Sheet Successfully"));
    }

    @RequestMapping(value = "admin/getDiamondSpecs", method = RequestMethod.POST,consumes = {"multipart/form-data"})
    public ResponseEntity<?> getDiamondSpecs(@PathVariable NonCertDiamondName diamondName, String sieveSize, ClarityAndColor clarityAndColor, double totalWeight) throws Exception{
        nonCertifiedDiamondService.getDiamondSpecs(new DiamondSpecsRequest(diamondName,sieveSize,clarityAndColor,totalWeight));
        return ResponseEntity.ok(new RestResponseStatus("Added Products from Successfully"));
    }

    @RequestMapping(value = "admin/getListOfDiamondSpecs", method = RequestMethod.POST,consumes = {"multipart/form-data"})
    public ResponseEntity<?> getListOfDiamondSpecs(@RequestBody ListOfDiamondSpecsRequest listOfDiamondSpecsRequest) throws Exception{
        List<DiamondSpecsResponse> diamondSpecsResponseList = nonCertifiedDiamondService.getListOfDiamondSpecs(listOfDiamondSpecsRequest.getDiamondSpecsRequestList());
        return ResponseEntity.ok(new ListOfDiamondSpecsResponse(diamondSpecsResponseList));
    }
}
