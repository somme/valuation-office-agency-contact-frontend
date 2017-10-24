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

package uk.gov.hmrc.valuationofficeagencycontactfrontend.forms

import play.api.data.FormError
import uk.gov.hmrc.valuationofficeagencycontactfrontend.forms.behaviours.FormBehaviours
import uk.gov.hmrc.valuationofficeagencycontactfrontend.models.ContactDetails

class ContactDetailsFormSpec extends FormBehaviours {

  val validData: Map[String, String] = Map(
    "firstName" -> "a",
    "lastName" -> "b",
    "email" -> "a@test.com",
    "confirmEmail" -> "a@test.com",
    "contactNumber" -> "1234567890"
  )

  val form = ContactDetailsForm()

  "ContactDetails form" must {
    behave like questionForm(ContactDetails("a", "b", "a@test.com", "a@test.com", "1234567890"))

    behave like formWithMandatoryTextFields("firstName", "lastName")

    "fail to bind when email is blank" in {
      val data = validData + ("email" -> "") + ("confirmEmail" -> "")
      val expectedError = error("email", "error.email")
      checkForError(form, data, expectedError)
    }

    "fail to bind when email is invalid" in {
      val data = validData + ("email" -> "a.test.com") + ("confirmEmail" -> "a.test.com")
      val expectedError = error("email", "error.email")
      checkForError(form, data, expectedError)
    }

    "fail to bind when emails don't match" in {
      val data = validData + ("confirmEmail" -> "ab@test.com")
      val expectedError = error("confirmEmail", "error.email.unmatched")
      checkForError(form, data, expectedError)
    }

    "fail to bind when emails don't match and second email is blank" in {
      val data = validData + ("confirmEmail" -> "")
      val expectedError = error("confirmEmail", "error.email.unmatched")
      checkForError(form, data, expectedError)
    }

    "EmailConstraint bind method should return Left(error.email.unmatched) if the emails don't match" in {
      val data = validData + ("confirmEmail" -> "ab@test.com")
      val result = EmailConstraint.bind("confirmEmail", data)
      result shouldBe Left(List(FormError("confirmEmail", "error.email.unmatched")))
    }

    "EmailConstraint bind method should return Right(email) if the emails are valid and match" in {
      val data = validData + ("confirmEmail" -> "a@test.com")
      val result = EmailConstraint.bind("confirmEmail", data)
      result shouldBe Right(data.getOrElse("email", ""))
    }

    "fail to bind when contact number is blank" in {
      val data = validData + ("contactNumber" -> "")
      val expectedError = Seq(error("contactNumber", "error.required"), error("contactNumber", "error.invalid_phone")).flatMap(e => e)
      checkForError(form, data, expectedError)
    }

    s"fail to bind when contact number is omitted" in {
      val data = validData - "contactNumber"
      val expectedError = error("contactNumber", "error.required")
      checkForError(form, data, expectedError)
    }

    s"fail to bind when contact number is invalid" in {
      val data = validData + ("contactNumber" -> "asdsa2332323232")
      val expectedError = error("contactNumber", "error.invalid_phone")
      checkForError(form, data, expectedError)
    }

    s"fail to bind when contact number length is less than 10" in {
      val data = validData + ("contactNumber" -> "123456789")
      val expectedError = error("contactNumber", "error.invalid_phone")
      checkForError(form, data, expectedError)
    }
  }

}
