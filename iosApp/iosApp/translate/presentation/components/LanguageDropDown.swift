//
//  LanguageDropDown.swift
//  iosApp
//
//  Created by Dmitry Sankovsky on 26.06.23.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct LanguageDropDown: View {
    var language: UiLanguage
    var isOpen: Bool
    var selecLanguage: (UiLanguage) -> Void
    
    var body: some View {
        Menu(
            content: {
                VStack {
                    ForEach(
                        UiLanguage.Companion().allLanguages,
                        id: \.self.language.langCode
                    ) { language in
                        LanguageDropDownItem(language: language) {
                            selecLanguage(language)
                        }
                    }
                }
            }, label: {
                HStack {
                    SmallLanguageIcon(language: language)
                    Text(language.language.name)
                        .foregroundColor(.lightBlue)
                    Image(systemName: isOpen ? "chevron.up" :"chevron.down")
                        .foregroundColor(.lightBlue)
                }
            }
        )
    }
}

struct LanguageDropDown_Previews: PreviewProvider {
    static var previews: some View {
        LanguageDropDown(
            language: UiLanguage(
                language: .german,
                imageName: "german"
            ),
            isOpen: true,
            selecLanguage: {Language in }
        )
    }
}
