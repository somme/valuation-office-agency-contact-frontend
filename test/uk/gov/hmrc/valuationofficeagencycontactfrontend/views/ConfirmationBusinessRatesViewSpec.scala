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

package uk.gov.hmrc.valuationofficeagencycontactfrontend.views

import uk.gov.hmrc.valuationofficeagencycontactfrontend.views.behaviours.ViewBehaviours
import uk.gov.hmrc.valuationofficeagencycontactfrontend.views.html.confirmationBusinessRates
import uk.gov.hmrc.valuationofficeagencycontactfrontend.models._
import uk.gov.hmrc.valuationofficeagencycontactfrontend.utils.{DateFormater}

class ConfirmationBusinessRatesViewSpec extends ViewBehaviours {

    val cd = ContactDetails("a", "b", "c", "d", "e")
    val confirmCd = ConfirmedContactDetails(cd)
    val ec = "council_tax"
    val ba = Some(BusinessRatesAddress("a", "b", "c", "d", "f", "h", "i"))
    val bSub = "council_tax_home_business"
    val tellUs = TellUsMore("Hello")
    var contact = Contact(confirmCd, None, ba, ec, bSub, tellUs.message)
    val date = DateFormater.returnDate()

    def view = () => confirmationBusinessRates(frontendAppConfig, contact, date)(fakeRequest, messages)

   "ConfirmationBusinessRates view" must {

    behave like normalPage(view, "confirmation", "reference.ndr", "enquirySummary", "whatHappensnext",  "para1", "para2", "para3", "section.enquiryType", "section.yourDetails", "section.propertyAddress" , "section.yourMessage")

    "contain a print button " in {
      val doc = asDocument(view())
      val printButton = doc.getElementById("print-button").text()
      val href = doc.getElementById("print-button").attr("href")
      assert(printButton == messages("site.print.button"))
      assert(href == "javascript:window.print()")
    }

  }

}