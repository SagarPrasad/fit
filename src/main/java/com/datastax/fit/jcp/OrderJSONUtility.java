/*
 * Copyright (c) 2016 JCPenney Co. All rights reserved.
 *
 */

package com.datastax.fit.jcp;

import java.util.Arrays;
import java.util.List;

/**
 * @author sagar
 */
public class OrderJSONUtility {


    private static List<String> validIndexableKeys;
    private static List<String> validFieldsName;

    static {
        validFieldsName = Arrays
                .asList("orderblob", "jsonfield");
    }

    static {
        validIndexableKeys = Arrays
                .asList("relationships.account.id",
                        "adjustments_.amount",
                        "intialEntryChannel",
                        "bag.items_.product.id",
                        "bag.items_.product.mailCode",
                        "billing.creditCards.cards_.avsResult",
                        "submittedDate",
                        "number",
                        "bag.items_.product.division",
                        "state",
                        "id",
                        "delivery.groups_.serviceLevels.data_.promises_.type",
                        "charges_.amount",
                        "billing.creditCards.cards_.authType",
                        "bag.items_.product.fsVendor",
                        "billing.creditCards.cards_.address.addressVid",
                        "bag.items_.product.monogram",
                        "bag.items_.product.subdivision",
                        "bag.items_.product.number",
                        "bag.items_.inventory.status",
                        "billing.creditCards.cards_.lastFour",
                        "delivery.groups_.address.poBox",
                        "bag.items_.prices_.type",
                        "delivery.groups_.address.city",
                        "delivery.groups_.address.standardizedValue",
                        "bag.items_.product.marketingLabel",
                        "bag.items_.sku.quantity",
                        "bag.items_.product.itemRestricted",
                        "bag.items_.product.isRecycleFeeEligible",
                        "bag.items_.product.factoryShipLeadTimeUOM",
                        "adjustments_.value",
                        "delivery.groups_.address.state",
                        "charity.selected",
                        "adjustments_.subType",
                        "delivery.groups_.serviceLevels.data_.duration",
                        "bag.items_.product.oversized",
                        "bag.items_.isShippingThresholdExcluded",
                        "adjustments_.restrictionType",
                        "bag.items_.product.categoryId",
                        "bag.items_.inventory.quantity",
                        "bag.items_.product.fineJewelry",
                        "bag.items_.product.factoryShipLeadTimeQty",
                        "billing.creditCards.cards_.type",
                        "bag.items_.inventory.urgency",
                        "bag.items_.product.truckable",
                        "delivery.groups_.address.commercial",
                        "delivery.groups_.serviceLevels.data_.holidayShipping",
                        "delivery.groups_.address.zipExtension",
                        "bag.items_.product.factoryShip",
                        "bag.items_.bundleDiscountApplied",
                        "delivery.groups_.address.directShipStoreNum",
                        "finalEntryChannel",
                        "billing.creditCards.cards_.responseCode",
                        "bag.items_.product.type",
                        "bag.items_.product.salonItem",
                        "delivery.groups_.serviceLevels.data_.charge",
                        "bag.items_.sku.id",
                        "delivery.groups_.serviceLevels.data_.selected",
                        "bag.items_.quantity",
                        "adjustments_.status",
                        "billing.creditCards.cards_.cvvResult",
                        "bag.items_.product.brandName",
                        "delivery.groups_.address.country",
                        "delivery.groups_.address.primeDistributionCenterId",
                        "delivery.groups_.type",
                        "bag.items_.product.entity",
                        "billing.creditCards.cards_.address.state",
                        "bag.items_.product.isDisposalFeeEligible",
                        "adjustments_.reason",
                        "bag.items_.product.commodityCode",
                        "bag.items_.product.willCallStoreIndicator",
                        "totals_.amount",
                        "bag.items_.activePriceType",
                        "adjustments_.code",
                        "billing.creditCards.cards_.address.zip",
                        "delivery.groups_.serviceLevels.data_.code",
                        "bag.items_.prices_.amount",
                        "billing.creditCards.cards_.encryptionType",
                        "bag.items_.product.digital",
                        "billing.creditCards.cards_.approvalCode",
                        "billing.creditCards.cards_.amount",
                        "isTestOrder",
                        "bag.itemCount",
                        "adjustments_.paymentType",
                        "bag.items_.product.programTypeCode",
                        "billing.creditCards.cards_.status",
                        "clientIpAddress",
                        "orderSource",
                        "bag.items_.isExchangeShipping",
                        "delivery.groups_.address.stateBit",
                        "bag.items_.lastModifiedDate",
                        "bag.items_.product.sephora",
                        "bag.items_.prices_.label",
                        "delivery.groups_.address.zip",
                        "billing.creditCards.cards_.requestedAmount",
                        "isPriceAccurate");
    }

    public static List<String> getIndexableKeys() {
        return validIndexableKeys;
    }

    public static List<String> getOrderJsonFieldNames() {
        return validFieldsName;
    }

 }
