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

import javax.inject.{Inject, Singleton}

import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.mvc.{Action, AnyContent}
import uk.gov.hmrc.play.bootstrap.controller.FrontendController
import uk.gov.hmrc.valuationofficeagencycontactfrontend.FrontendAppConfig
import uk.gov.hmrc.valuationofficeagencycontactfrontend.connectors.LightweightContactEventsConnector
import uk.gov.hmrc.valuationofficeagencycontactfrontend.models.{ConfirmedContactDetails, ContactDetails, Contact, CouncilTaxAddress}
import uk.gov.hmrc.valuationofficeagencycontactfrontend.views.html.confirmationCouncilTax

@Singleton()
class ConfirmCouncilTaxController @Inject()(val appConfig: FrontendAppConfig,
                                            val messagesApi: MessagesApi,
                                            val connector: LightweightContactEventsConnector) extends FrontendController with I18nSupport {

  def onPageLoad: Action[AnyContent] = Action { implicit request =>
    val contactModel = ???
    val councilTaxAddress: CouncilTaxAddress = ???

    val result = connector.send(contactModel)
    Ok(confirmationCouncilTax(appConfig, councilTaxAddress))
  }
}