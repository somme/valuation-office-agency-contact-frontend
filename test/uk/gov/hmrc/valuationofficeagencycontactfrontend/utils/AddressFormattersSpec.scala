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

package uk.gov.hmrc.valuationofficeagencycontactfrontend.utils

import uk.gov.hmrc.valuationofficeagencycontactfrontend.SpecBase
import uk.gov.hmrc.valuationofficeagencycontactfrontend.models.{BusinessRatesAddress, CouncilTaxAddress}
import uk.gov.hmrc.valuationofficeagencycontactfrontend.utils.AddressFormatters._

class AddressFormattersSpec extends SpecBase {

  "Address Formatter" must {

    "Given a complete Council Tax Address it should generate a formatted string using the given interstitial" in {
      val cta = CouncilTaxAddress("a", "b", "c", "d", "e")
      formattedCouncilTaxAddress(Some(cta), "<br/>") mustBe "a<br/>b<br/>c<br/>d<br/>e"
    }

    "Given a Council Tax Address with elements that have too many spaces it should generate a formatted string using the given interstitial" in {
      val cta = CouncilTaxAddress(" a ", " b ", " c ", " d ", " e ")
      formattedCouncilTaxAddress(Some(cta), "<br/>") mustBe "a<br/>b<br/>c<br/>d<br/>e"
    }

    "Given no Council Tax Address it should generate am empty string" in {
      formattedCouncilTaxAddress(None, "<br/>") mustBe ""
    }

    "Given a Sequence with three strings insert the interstitials" in {
      insertInterstitials(Seq("a", "b", "c"), ",") mustBe "a,b,c"
    }

    "Given an empty sequence return an empty String" in {
      insertInterstitials(Seq(), ",") mustBe ""

    }

    "Given a sequence of strings that have leading or trailing spaces return the formatted string without the leading or trailing spaces" in {
      insertInterstitials(Seq(" a ", " b ", " c "), ",") mustBe "a,b,c"
    }

    "Given a complete Business Rates Address it should generate a formatted string using the given interstitial" in {
      val  bra = BusinessRatesAddress("a", "b", "c", "d", "e", "f", "g")
      formattedBusinessRatesAddress(Some(bra), "<br/>") mustBe "a<br/>b<br/>c<br/>d<br/>e<br/>f<br/>g"

    }

    "Given a Business Rates Address with elements that have too many spaces it should generate a formatted string using the given interstitial" in {
      val  bra = BusinessRatesAddress(" a ", " b ", " c ", " d ", " e ", " f ", " g ")
      formattedBusinessRatesAddress(Some(bra), "<br/>") mustBe "a<br/>b<br/>c<br/>d<br/>e<br/>f<br/>g"
    }

    "Given no Business Rates Address it should generate am empty string" in {
      formattedBusinessRatesAddress(None, "<br/>") mustBe ""
    }
  }
}
