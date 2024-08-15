/*
 * Copyright 2024 BlackOrbs (blackorbs@icloud.com)
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License
 */

package blackorbs.dev.payapp.ui.main.transfer

import android.content.Context
import android.util.AttributeSet

class SelectorTextView(context: Context?, attrs: AttributeSet?, defStyleAttr: Int): com.google.android.material.textfield.MaterialAutoCompleteTextView(context!!, attrs, defStyleAttr) {
    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, android.R.attr.webViewStyle)

    override fun replaceText(text: CharSequence?){}

    override fun getFreezesText() = false
}