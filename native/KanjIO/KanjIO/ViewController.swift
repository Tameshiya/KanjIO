//
//  ViewController.swift
//  KanjIO
//
//  Created by Lerochka on 08/01/2020.
//  Copyright © 2020 安藤株式会社. All rights reserved.
//

import UIKit

class ViewController: UIViewController {
    
    var data: [Food]? = nil
    
    let presenter = FoodEconomyPresenter(repository: FoodEconomyRepository())
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.title = "EconomyStrategy"
        presenter.setView(view: self)
    }
    
    deinit {
        data = nil
        presenter.releaseView()
    }

    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return data!.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell: FoodCell = tableView.dequeueReusableCell(withIdentifier: "foodCell", for: indexPath) as! FoodCell
        cell.dayOfWeek.text = data?[indexPath.row].dayOfWeek.name
        cell.name.text = data?[indexPath.row].name
        cell.day.text = data?[indexPath.row].day.name
        return cell
    }
    
    func showFoodList(foodList: [Food]) {
        data = foodList
        listView.register(UINib(nibName: "FoodCell", bundle: nil), forCellReuseIdentifier: "foodCell")
    }
    
    func setFabListener(listener: @escaping () -> Void) {
        
    }
    
    func setFoodListItemListener(listener: @escaping (Food) -> Void) {
        
    }
    
    func setShuffleListener(listener: @escaping () -> Void) {
        
    }


}

