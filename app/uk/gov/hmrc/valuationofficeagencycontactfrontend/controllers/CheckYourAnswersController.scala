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

package uk.gov.hmrc.valuationofficeagencycontactfrontend.controllers

import com.google.inject.Inject
import play.api.Logger
import play.api.i18n.{I18nSupport, MessagesApi}
import uk.gov.hmrc.valuationofficeagencycontactfrontend.controllers.actions.{DataRequiredAction, DataRetrievalAction}
import uk.gov.hmrc.valuationofficeagencycontactfrontend.utils.CheckYourAnswersHelper
import uk.gov.hmrc.valuationofficeagencycontactfrontend.viewmodels.AnswerSection
import uk.gov.hmrc.valuationofficeagencycontactfrontend.views.html.check_your_answers
import uk.gov.hmrc.valuationofficeagencycontactfrontend.FrontendAppConfig
import uk.gov.hmrc.play.bootstrap.controller.FrontendController

class CheckYourAnswersController @Inject()(appConfig: FrontendAppConfig,
                                           override val messagesApi: MessagesApi,
                                           getData: DataRetrievalAction,
                                           requireData: DataRequiredAction) extends FrontendController with I18nSupport {

  def onPageLoad() = (getData andThen requireData) {
    implicit request =>
      val checkYourAnswersHelper = new CheckYourAnswersHelper(request.userAnswers)

      val sections = request.userAnswers.enquiryCategory match {
        case Some("council_tax") => Seq(AnswerSection(None, Seq(checkYourAnswersHelper.enquiryCategory, checkYourAnswersHelper.contactDetails, checkYourAnswersHelper.councilTaxAddress, checkYourAnswersHelper.councilTaxSubcategory).flatten))
        case Some("business_rates") => Seq(AnswerSection(None, Seq(checkYourAnswersHelper.enquiryCategory, checkYourAnswersHelper.contactDetails, checkYourAnswersHelper.businessRatesAddress, checkYourAnswersHelper.businessRatesSubcategory).flatten))
        case Some(_) => Seq()
        case None => {
          Logger.warn("Navigation for Check your answers page reached without selection of enquiry by controller")
          throw new RuntimeException("Navigation for check your anwsers page reached without selection of enquiry by controller")
        }
      }
      //val sections = Seq(AnswerSection(None, Seq(checkYourAnswersHelper.enquiryCategory, checkYourAnswersHelper.contactDetails, checkYourAnswersHelper.councilTaxAddress).flatten))
      Ok(check_your_answers(appConfig, sections))
  }
}
