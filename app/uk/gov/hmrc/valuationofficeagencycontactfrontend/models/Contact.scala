/*
 * Copyright 2017 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.valuationofficeagencycontactfrontend.models

import play.api.libs.json.Json

case class Contact(contact: ConfirmedContactDetails,
                   councilTaxAddress: Option[CouncilTaxAddress],
                   businessRatesAddress: Option[BusinessRatesAddress],
                   enquiryCategory: String,
                   subEnquiryCategory: String,
                   message: String)

object Contact {
  implicit val format = Json.format[Contact]

  def apply(message: String,
            enquiryCategory: String,
            subEnquiryCategory: String,
            contact: ContactDetails,
            councilTaxAddress: CouncilTaxAddress): Contact =
    Contact(ConfirmedContactDetails(contact), Some(councilTaxAddress), None, enquiryCategory, subEnquiryCategory, message)

  def apply(message: String,
            enquiryCategory: String,
            subEnquiryCategory: String,
            contact: ContactDetails,
            businessRatesAddress: BusinessRatesAddress): Contact =
    Contact(ConfirmedContactDetails(contact), None, Some(businessRatesAddress), enquiryCategory, subEnquiryCategory, message)
}