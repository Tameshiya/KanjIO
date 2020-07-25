import Foundation
import UIKit
import shared

class KanjiGroupDialogView : NSObject, KanjiGroupView, UIPickerViewDataSource, UIPickerViewDelegate {
    
    let kanjiGroupsPresenter: KanjiGroupsPresenter
    let pickerView: UIPickerView
    let groupSelectorDialog: UIAlertController
    let kanjiListViewController: UIViewController
    
    //FIXME!!!!!!! side effect, code smell
    var groupsLabels: [String]? = nil
    var levelsLabels: [String]? = nil
    
    init(kanjiGroupPresenter: KanjiGroupsPresenter, kanjiListViewController: UIViewController) {
        self.kanjiGroupsPresenter = kanjiGroupPresenter
        self.kanjiListViewController = kanjiListViewController
        self.pickerView = UIPickerView(frame: CGRect(x: 0, y: 0, width: 250, height: 300))
        self.groupSelectorDialog = UIAlertController(title: "Choose level", message: "", preferredStyle: UIAlertController.Style.alert)
        super.init()
        self.kanjiGroupsPresenter.attachView(view: self)
        self.pickerView.delegate = self
        self.pickerView.dataSource = self
        setupDialog()
        self.kanjiGroupsPresenter.renderKanjiGroupsView()
    }
    
    func showKanjiGroupsDialog(kanjiGroupsLabels: [String], initialKanjiGroupsLevelsLabels: [String]) {
        self.groupsLabels = kanjiGroupsLabels
        self.levelsLabels = initialKanjiGroupsLevelsLabels
        self.pickerView.reloadAllComponents()
        kanjiListViewController.present(self.groupSelectorDialog, animated: true)
    }
    
    func updateLevels(kanjiLevelsLabels: [String]) {
        self.levelsLabels = kanjiLevelsLabels
        self.pickerView.reloadComponent(1)
    }
    
    func updateTitle(kanjiGroupTitle: String) {
        kanjiListViewController.title = kanjiGroupTitle
    }
    
    func numberOfComponents(in pickerView: UIPickerView) -> Int {
        return 2
    }
        
    func pickerView(_ pickerView: UIPickerView, numberOfRowsInComponent component: Int) -> Int {
        if (component == 0) {
            return groupsLabels?.count ?? 0
        }
        return levelsLabels?.count ?? 0
    }
    
    func pickerView(_ pickerView: UIPickerView, titleForRow row: Int, forComponent component: Int) -> String? {
        if (component == 0) {
            return groupsLabels?[row] ?? "?"
        }
        return levelsLabels?[row] ?? "?"
    }
    
    func pickerView(_ pickerView: UIPickerView, didSelectRow row: Int, inComponent component: Int) {
        if (component == 0) {
            self.kanjiGroupsPresenter.updateLevels(newPosition: Int32(row))
        }
    }
    
    private func setupDialog() {
        let height:NSLayoutConstraint = NSLayoutConstraint(
            item: groupSelectorDialog.view!,
            attribute: NSLayoutConstraint.Attribute.height,
            relatedBy: NSLayoutConstraint.Relation.equal,
            toItem: nil,
            attribute: NSLayoutConstraint.Attribute.notAnAttribute,
            multiplier: 1,
            constant: 350
        )
        groupSelectorDialog.view.addConstraint(height);
        groupSelectorDialog.view.addSubview(pickerView)
        groupSelectorDialog.addAction(UIAlertAction(title: "Done", style: .default, handler: { (alert: UIAlertAction!) -> Void in
            let groupIndex = self.pickerView.selectedRow(inComponent: 0)
            let lvlIndex = self.pickerView.selectedRow(inComponent: 1)
            self.kanjiGroupsPresenter.submitKanjiGroup(groupPosition: Int32(groupIndex), levelPosition: Int32(lvlIndex))
        }))
        groupSelectorDialog.addAction(UIAlertAction(title: "Cancel", style: .cancel, handler: nil))
    }
    
}
