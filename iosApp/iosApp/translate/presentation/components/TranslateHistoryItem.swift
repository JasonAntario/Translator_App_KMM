//
//  TranslateHistoryItem.swift
//  iosApp
//
//  Created by Dmitry Sankovsky on 27.06.23.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct TranslateHistoryItem: View {
    var item: UiHistoryItem
    var onClick: () -> Void
    
    var body: some View {
        Button(action: onClick) {
            VStack {
                HStack {
                    SmallLanguageIcon(language: item.fromLanguage)
                        .padding(.trailing)
                    Text(item.fromText)
                        .foregroundColor(Color.lightBlue)
                        .font(.body)
                }
                .frame(maxWidth: .infinity, alignment: .leading)
                .padding(.bottom)
                
                HStack {
                    SmallLanguageIcon(language: item.toLanguage)
                        .padding(.trailing)
                    Text(item.toText)
                        .foregroundColor(Color.onSurface)
                        .font(.body.weight(.semibold))
                }
                .frame(maxWidth: .infinity, alignment: .leading)
            }
            .frame(maxWidth: .infinity)
            .padding()
            .gradientSurvace()
            .cornerRadius(15)
            .shadow(radius: 4)
        }
    }
}

struct TranslateHistoryItem_Previews: PreviewProvider {
    static var previews: some View {
        TranslateHistoryItem(
            item: UiHistoryItem(
                id: 0,
                fromText: "Hello there!",
                toText: "General Kenobi",
                fromLanguage: UiLanguage(language: .english, imageName: "english"),
                toLanguage: UiLanguage(language: .german, imageName: "german")
            ),
            onClick: {})
    }
}
