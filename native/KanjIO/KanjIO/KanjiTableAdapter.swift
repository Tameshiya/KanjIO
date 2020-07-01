//
//  KanjiTableAdapter.swift
//  KanjIO
//
//  Created by Lerochka on 02/07/2020.
//  Copyright © 2020 安藤株式会社. All rights reserved.
//

import UIKit
import shared

extension KanjiListViewController: UITableViewDataSource {
        
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return kanjiList.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "cell", for: indexPath)
        let kanjiUnicodeScalar: UnicodeScalar = UnicodeScalar(Int(truncatingIfNeeded: kanjiList[indexPath.row].code)) ?? "?"
        cell.textLabel!.text = Character(kanjiUnicodeScalar).description
        return cell
    }
}
