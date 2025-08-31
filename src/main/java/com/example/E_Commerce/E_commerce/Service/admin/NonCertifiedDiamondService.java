package com.example.E_Commerce.E_commerce.Service.admin;


import com.example.E_Commerce.E_commerce.Models.Admin.entities.DiamondChart;
import com.example.E_Commerce.E_commerce.Models.Admin.entities.DiamondPricing;
import com.example.E_Commerce.E_commerce.Models.Admin.entities.SieveSize;
import com.example.E_Commerce.E_commerce.Models.Admin.entities.embedables.Diamond;
import com.example.E_Commerce.E_commerce.Models.Admin.entities.embedables.DiamondIdentity;
import com.example.E_Commerce.E_commerce.Models.Admin.entities.models.ClarityAndColor;
import com.example.E_Commerce.E_commerce.Models.Admin.entities.models.NonCertDiamondName;
import com.example.E_Commerce.E_commerce.Models.Admin.request.DiamondSpecsRequest;
import com.example.E_Commerce.E_commerce.Models.Admin.request.SheetDocument;
import com.example.E_Commerce.E_commerce.Models.Admin.response.DiamondSpecs;
import com.example.E_Commerce.E_commerce.Models.Admin.response.DiamondSpecsResponse;
import com.example.E_Commerce.E_commerce.Repository.Admin.DiamondChartRepository;
import com.example.E_Commerce.E_commerce.Repository.Admin.DiamondPriceRepository;
import com.example.E_Commerce.E_commerce.Repository.Admin.SieveSizeRepository;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.apache.poi.ss.usermodel.CellType.NUMERIC;
import static org.apache.poi.ss.usermodel.CellType.STRING;

@Service
public class NonCertifiedDiamondService {

    private static final Logger logger = LoggerFactory.getLogger(NonCertifiedDiamondService.class);

    private final DiamondChartRepository diamondChartRepository;
    private final DiamondPriceRepository diamondPriceRepository;
    private final SieveSizeRepository sieveSizeRepository;

    public NonCertifiedDiamondService(DiamondChartRepository diamondChartRepository,
                                      DiamondPriceRepository diamondPriceRepository,
                                      SieveSizeRepository sieveSizeRepository) {
        this.diamondChartRepository = diamondChartRepository;
        this.diamondPriceRepository = diamondPriceRepository;
        this.sieveSizeRepository = sieveSizeRepository;
    }

    @Transactional
    public void addSieveSizes(Sheet sheet) throws IOException {
        int i = 0;
        List<SieveSize> sieveSizeList = new ArrayList<>();
        Row row = sheet.getRow(i);
        if (row != null && row.getCell(0) != null &&
                row.getCell(0).getCellType() == STRING &&
                "Sieve Size".equals(row.getCell(0).getStringCellValue())) {

            for (i = 1; ; i++) {
                row = sheet.getRow(i);
                if (row == null || row.getCell(0) == null ||
                        row.getCell(0).getCellType() != STRING ||
                        row.getCell(0).getStringCellValue().isEmpty()) {
                    logger.info("No more sieve sizes found at row {}", i);
                    break;
                }
                sieveSizeList.add(new SieveSize(row.getCell(0).getStringCellValue()));
            }
            sieveSizeRepository.saveAll(sieveSizeList);
            logger.info("Saved {} sieve sizes", sieveSizeList.size());
        } else {
            logger.warn("Sieve Size header not found in sheet");
        }
    }

    @Transactional
    public void addDiamondChart(Sheet sheet, String name) {
        int i = 0;
        boolean flag = true;
        List<DiamondChart> diamondChartList = new ArrayList<>();
        Row row = sheet.getRow(i);

        if (row != null &&
                row.getCell(0) != null && row.getCell(0).getCellType() == STRING &&
                row.getCell(1) != null && row.getCell(1).getCellType() == STRING &&
                row.getCell(2) != null && row.getCell(2).getCellType() == STRING &&
                "Sieve Size".equals(row.getCell(0).getStringCellValue()) &&
                "MM Size".equals(row.getCell(1).getStringCellValue()) &&
                "Diamond Weight".equals(row.getCell(2).getStringCellValue())) {

            for (i = 1; ; i++) {
                row = sheet.getRow(i);
                if (row == null || row.getCell(0) == null ||
                        row.getCell(0).getCellType() != STRING ||
                        row.getCell(0).getStringCellValue().isEmpty()) {
                    logger.info("No more diamond chart entries found at row {}", i);
                    break;
                }

                Optional<SieveSize> sieveSizeOpt = sieveSizeRepository.findById(row.getCell(0).getStringCellValue());
                if (row.getCell(1) != null && row.getCell(2) != null) {
                    if (sieveSizeOpt.isPresent() &&
                            row.getCell(1).getCellType() == NUMERIC &&
                            row.getCell(2).getCellType() == NUMERIC) {

                        Diamond diamond = new Diamond(sieveSizeOpt.get(), NonCertDiamondName.valueOf(name), row.getCell(1).getNumericCellValue());
                        DiamondChart diamondChart = new DiamondChart(diamond, row.getCell(2).getNumericCellValue());
                        diamondChartList.add(diamondChart);

                    } else {
                        flag = false;
                        logger.warn("Invalid data at row {} for diamond chart '{}'", i, name);
                        break;
                    }
                } else {
                    logger.info("Missing MM Size or Diamond Weight at row {}", i);
                    break;
                }
            }

            if (flag) {
                diamondChartRepository.saveAll(diamondChartList);
                logger.info("Saved diamond chart '{}' with {} entries", name, diamondChartList.size());
            }
        } else {
            logger.warn("Diamond chart header not found or invalid for '{}'", name);
        }
    }

    @Transactional
    public void addPrices(Sheet sheet) throws IOException {
        int i, j, k;
        boolean flag = true;
        List<DiamondPricing> diamondPricingList = new ArrayList<>();

        Row rowWR = sheet.getRow(0);
        Row rowB = sheet.getRow(6);
        Row rowP = sheet.getRow(12);

        List<String> greaterThan = Arrays.asList(">11", ">6.5", ">2", ">-2");
        List<Double> lessThan = Arrays.asList(Double.POSITIVE_INFINITY, 11.0, 6.5, 2.0);
        List<Double> greaterDThan = Arrays.asList(11.0, 6.5, 2.0, -2.0);
        List<String> diamondNames = Stream.of(NonCertDiamondName.values())
                .map(Enum::name)
                .collect(Collectors.toList());

        List<String> clarityAndColor = Arrays.asList("IFtoVVS2_DtoF", "VS1toVS2_GtoI", "SI1toSI2_JtoK",
                "VVS1toVVS2_GtoI", "VS1toVS2_JtoK", "SI1toSI2_LtoM");

        // Validate header rows
        boolean validHeaders = validatePriceSheetHeaders(rowWR, rowB, rowP);
        if (!validHeaders) {
            logger.warn("Price sheet headers are invalid");
            return;
        }

        for (i = 1, j = 7, k = 13; i < 5; i++, j++, k++) {
            rowWR = sheet.getRow(i);
            rowB = sheet.getRow(j);
            rowP = sheet.getRow(k);

            if (rowWR == null || rowB == null || rowP == null) {
                logger.warn("Missing rows at indices {}, {}, {}", i, j, k);
                flag = false;
                break;
            }

            if (checkRowFirstCell(rowWR, greaterThan.get(i - 1)) &&
                    checkRowFirstCell(rowB, greaterThan.get(i - 1)) &&
                    checkRowFirstCell(rowP, greaterThan.get(i - 1))) {

                for (int l = 1; l <= 6; l++) {
                    if (checkCellTypeNumeric(rowWR, l) && checkCellTypeNumeric(rowB, l) && checkCellTypeNumeric(rowP, l)) {
                        diamondPricingList.add(new DiamondPricing(
                                new DiamondIdentity(NonCertDiamondName.valueOf(diamondNames.get(0)),
                                        ClarityAndColor.valueOf(clarityAndColor.get(l - 1)),
                                        greaterDThan.get(i - 1), lessThan.get(i - 1)),
                                rowWR.getCell(l).getNumericCellValue()));

                        diamondPricingList.add(new DiamondPricing(
                                new DiamondIdentity(NonCertDiamondName.valueOf(diamondNames.get(1)),
                                        ClarityAndColor.valueOf(clarityAndColor.get(l - 1)),
                                        greaterDThan.get(i - 1), lessThan.get(i - 1)),
                                rowB.getCell(l).getNumericCellValue()));

                        diamondPricingList.add(new DiamondPricing(
                                new DiamondIdentity(NonCertDiamondName.valueOf(diamondNames.get(2)),
                                        ClarityAndColor.valueOf(clarityAndColor.get(l - 1)),
                                        greaterDThan.get(i - 1), lessThan.get(i - 1)),
                                rowP.getCell(l).getNumericCellValue()));
                    } else {
                        logger.warn("Non-numeric cell found at row {}, column {}", i, l);
                        flag = false;
                        break;
                    }
                }
            } else {
                logger.warn("Row first cell mismatch at rows {}, {}, {}", i, j, k);
                flag = false;
                break;
            }
        }

        if (flag) {
            diamondPriceRepository.saveAll(diamondPricingList);
            logger.info("Saved {} diamond pricing entries", diamondPricingList.size());
        }
    }

    private boolean validatePriceSheetHeaders(Row rowWR, Row rowB, Row rowP) {
        if (rowWR == null || rowB == null || rowP == null) return false;

        String[] expectedHeaders = {"WR", "IFtoVVS2_DtoF 55", "VS1toVS2_GtoI 53", "SI1toSI2_JtoK 51",
                "VVS1toVVS2_GtoI 49", "VS1toVS2_JtoK 47", "SI1toSI2_LtoM 45"};

        String[] expectedHeadersB = {"B", "IFtoVVS2_DtoF 55", "VS1toVS2_GtoI 53", "SI1toSI2_JtoK 51",
                "VVS1toVVS2_GtoI 49", "VS1toVS2_JtoK 47", "SI1toSI2_LtoM 45"};

        String[] expectedHeadersP = {"P", "IFtoVVS2_DtoF 55", "VS1toVS2_GtoI 53", "SI1toSI2_JtoK 51",
                "VVS1toVVS2_GtoI 49", "VS1toVS2_JtoK 47", "SI1toSI2_LtoM 45"};

        return checkRowHeaders(rowWR, expectedHeaders) &&
                checkRowHeaders(rowB, expectedHeadersB) &&
                checkRowHeaders(rowP, expectedHeadersP);
    }

    private boolean checkRowHeaders(Row row, String[] expectedHeaders) {
        if (row == null) return false;
        for (int i = 0; i < expectedHeaders.length; i++) {
            Cell cell = row.getCell(i);
            if (cell == null || cell.getCellType() != STRING || !expectedHeaders[i].equals(cell.getStringCellValue())) {
                return false;
            }
        }
        return true;
    }

    private boolean checkRowFirstCell(Row row, String expectedValue) {
        if (row == null) return false;
        Cell cell = row.getCell(0);
        return cell != null && cell.getCellType() == STRING && expectedValue.equals(cell.getStringCellValue());
    }

    private boolean checkCellTypeNumeric(Row row, int cellIndex) {
        if (row == null) return false;
        Cell cell = row.getCell(cellIndex);
        return cell != null && cell.getCellType() == NUMERIC;
    }

    public void addDiamondPrices(SheetDocument sheetDocument, boolean onlyPricing) throws IOException {
        try (Workbook workbook = new XSSFWorkbook(sheetDocument.getProductSheet().getInputStream())) {
            if (!onlyPricing) {
                Sheet sieveSizeSheet = workbook.getSheet("SieveSize");
                addSieveSizes(sieveSizeSheet);

                Sheet whiteRoundSheet = workbook.getSheet("WhiteRound");
                addDiamondChart(whiteRoundSheet, "WhiteRound");

                Sheet baguetteSheet = workbook.getSheet("Baguette");
                addDiamondChart(baguetteSheet, "Baguette");

                Sheet princessSheet = workbook.getSheet("Princess");
                addDiamondChart(princessSheet, "Princess");
            }
            Sheet pricingSheet = workbook.getSheet("Pricing");
            addPrices(pricingSheet);
        }
    }

    public void addOnlyDiamondChart(SheetDocument sheetDocument) throws IOException {
        try (Workbook workbook = new XSSFWorkbook(sheetDocument.getProductSheet().getInputStream())) {
            Sheet sieveSizeSheet = workbook.getSheet("SieveSize");
            addSieveSizes(sieveSizeSheet);

            Sheet whiteRoundSheet = workbook.getSheet("WhiteRound");
            addDiamondChart(whiteRoundSheet, "WhiteRound");

            Sheet baguetteSheet = workbook.getSheet("Baguette");
            addDiamondChart(baguetteSheet, "Baguette");

            Sheet princessSheet = workbook.getSheet("Princess");
            addDiamondChart(princessSheet, "Princess");
        }
    }

    public DiamondSpecsResponse getDiamondSpecs(DiamondSpecsRequest diamondSpecsRequest) {
        Optional<SieveSize> sieveOpt = sieveSizeRepository.findById(diamondSpecsRequest.getSieveSize());
        if (sieveOpt.isPresent()) {
            Optional<DiamondChart> diamondChartOpt = diamondChartRepository.findByDiamondSieveSizeAndDiamondNonCertDiamondName(
                    sieveOpt.get(), diamondSpecsRequest.getDiamondName());
            if (diamondChartOpt.isPresent()) {
                List<DiamondPricing> diamondPricing = getDiamondPrice(
                        diamondChartOpt.get().getDiamond().getNonCertDiamondName(),
                        diamondSpecsRequest.getClarityAndColor(),
                        sieveOpt.get());
                if (diamondPricing.size() == 1) {
                    double price = diamondPricing.get(0).getPrice();
                    double perDiamondWeight = diamondChartOpt.get().getWeight();
                    int numberOfDiamonds = (int) Math.floor(diamondSpecsRequest.getTotalWeight() / perDiamondWeight);
                    return new DiamondSpecsResponse(price, perDiamondWeight, numberOfDiamonds, price * numberOfDiamonds);
                }
            }
        }
        return null;
    }

    public DiamondSpecs getDiamondSpecs(SieveSize sieveSize, NonCertDiamondName nonCertDiamondName,
                                        ClarityAndColor clarityAndColor, double totalWeight) {
        Optional<DiamondChart> diamondChartOpt = diamondChartRepository.findByDiamondSieveSizeAndDiamondNonCertDiamondName(
                sieveSize, nonCertDiamondName);
        if (diamondChartOpt.isPresent() && diamondChartOpt.get().getDiamond().getNonCertDiamondName() != null) {
            List<DiamondPricing> diamondPricing = getDiamondPrice(
                    diamondChartOpt.get().getDiamond().getNonCertDiamondName(),
                    clarityAndColor,
                    sieveSize);
            if (diamondPricing.size() == 1) {
                double price = diamondPricing.get(0).getPrice();
                int numberOfDiamonds = (int) Math.ceil(totalWeight / diamondChartOpt.get().getWeight());
                return new DiamondSpecs(sieveSize, totalWeight, price, numberOfDiamonds);
            }
        }
        return null;
    }

    public List<DiamondSpecsResponse> getListOfDiamondSpecs(List<DiamondSpecsRequest> diamondSpecsRequestList) {
        List<DiamondSpecsResponse> diamondSpecsResponseList = new ArrayList<>();
        for (DiamondSpecsRequest diamondSpecsRequest : diamondSpecsRequestList) {
            DiamondSpecsResponse response = getDiamondSpecs(diamondSpecsRequest);
            if (response != null) {
                diamondSpecsResponseList.add(response);
            }
        }
        return diamondSpecsResponseList;
    }

    public List<SieveSize> getSieves(double sieveSize) {
        return sieveSizeRepository.findAll((Specification<SieveSize>) (root, query, cb) -> {
            Predicate predicate = cb.lessThanOrEqualTo(root.get("upperLimit"), sieveSize);
            return predicate;
        });
    }

    public List<DiamondPricing> getDiamondPrice(NonCertDiamondName nonCertDiamondName,
                                                ClarityAndColor clarityAndColor,
                                                SieveSize sieveSize) {
        return diamondPriceRepository.findAll((Specification<DiamondPricing>) (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.equal(root.get("diamondIdentity").get("nonCertDiamondName"), nonCertDiamondName));
            predicates.add(cb.equal(root.get("diamondIdentity").get("clarityAndColor"), clarityAndColor));
            predicates.add(cb.lessThanOrEqualTo(root.get("diamondIdentity").get("sieveSizeGreaterThan"), sieveSize.getLowerLimit()));
            predicates.add(cb.greaterThanOrEqualTo(root.get("diamondIdentity").get("sieveSizeLessThan"), sieveSize.getUpperLimit()));
            return cb.and(predicates.toArray(new Predicate[0]));
        });
    }

    @Transactional
    public void rollup() {
        diamondPriceRepository.deleteAll();
        logger.info("Deleted all diamond pricing entries");
    }
}