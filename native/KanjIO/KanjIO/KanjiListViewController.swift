import UIKit
import shared

class KanjiListViewController: UIViewController, KanjiListView, UITableViewDelegate {
    
    private let kanjiTableView = UITableView()
    private var safeArea: UILayoutGuide!
    private var kanjiPresenter: KanjiPresenter!
    //todo privateにする必要があるけど、あらゆる手段を調べる。
    var kanjiList = [Kanji_]()
    
    let levels = ["N5", "N4", "N3", "N2", "N1"]
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        setupSelectors()
        
        //todo move to common part
        let defaults = UserDefaults.standard
        let isPreloaded = defaults.bool(forKey: "isPreloaded")
        if !isPreloaded {
            prepopulateDbBitwicely(dbName: "KanjiDb.db")
            defaults.set(true, forKey: "isPreloaded")
        }
        let db: KanjiDatabase = KanjiDatabase()
        db.defaultDriver()
        view.backgroundColor = .white
        safeArea = view.layoutMarginsGuide
        setupTableView()
        let kanjiQueries: KanjiQueries = db.instance.kanjiQueries
        let kanjiRepository: KanjiRepository = JLPTKanjiRepository(kanjiGroup: KanjiGroup.jlpt, kanjiQueries: kanjiQueries)
        let kanjiInteractor = KanjiInteractorImpl(kanjiRepository: kanjiRepository)
        self.kanjiPresenter = KanjiPresenter(interactor: kanjiInteractor)
        kanjiPresenter.attachView(view: self)
        kanjiPresenter.startFlow()
        //self.title = "EconomyStrategy"
    }
    
    func setTitle(currentKanjiGroup: KanjiGroup) {
        title = currentKanjiGroup.name
    }
    
    func showList(list: [Kanji_]) {
        kanjiList = list
        kanjiTableView.reloadData()
    }
    
    //todo to common layer
    func prepopulateDbBitwicely(dbName: String) {
        let bundlePath = Bundle.main.path(forResource: "KanjiDb", ofType: ".db")
        
        let destPath = NSSearchPathForDirectoriesInDomains(.applicationSupportDirectory, .userDomainMask, true).first!
        let fileManager = FileManager.default
        let databasesFolder = URL(fileURLWithPath:destPath).appendingPathComponent("databases", isDirectory: true)
        if (!fileManager.fileExists(atPath: databasesFolder.path)) {
            //ここは「databases」というフォルダーの存在を確認しなければならない。todo カスタム・フォルダーを指定してみること。
            do {
                try fileManager.createDirectory(at: databasesFolder, withIntermediateDirectories: true, attributes: nil)
            } catch {
                print("全部終わった。")
            }
        }
        let fullDestPath = databasesFolder.appendingPathComponent("KanjiDb.db")
        print(fullDestPath)
        if fileManager.fileExists(atPath: fullDestPath.path){
            print("Database file is exist")
            print(fileManager.fileExists(atPath: bundlePath!))
        } else {
            do {
                try fileManager.copyItem(atPath: bundlePath!, toPath: fullDestPath.path)
            } catch {
                print("\n",error)
            }
        }
    }
    
    @objc private func selectLevel() {
        setupLevelsPickerView()
    }
    
    @objc private func selectExam() {
        print("select exam")
    }
    
    private func setupTableView() {
        view.addSubview(kanjiTableView)
        //todo どうやって違うファイルに移せるのかを考えてみること。
        kanjiTableView.dataSource = self
        kanjiTableView.delegate = self
        kanjiTableView.translatesAutoresizingMaskIntoConstraints = false
        kanjiTableView.topAnchor.constraint(equalTo: safeArea.topAnchor).isActive = true
        kanjiTableView.leftAnchor.constraint(equalTo: view.leftAnchor).isActive = true
        kanjiTableView.bottomAnchor.constraint(equalTo: view.bottomAnchor).isActive = true
        kanjiTableView.rightAnchor.constraint(equalTo: view.rightAnchor).isActive = true
        kanjiTableView.register(UITableViewCell.self, forCellReuseIdentifier: "cell")
    }
    
    private func setupLevelsPickerView() {
        //todo custom layout
        let pickerView = UIPickerView(frame: CGRect(x: 0, y: 0, width: 250, height: 300))
        pickerView.delegate = self
        pickerView.dataSource = self
        let editRadiusAlert = UIAlertController(title: "Choose level", message: "", preferredStyle: UIAlertController.Style.alert)
        let height:NSLayoutConstraint = NSLayoutConstraint(
            item: editRadiusAlert.view!,
            attribute: NSLayoutConstraint.Attribute.height,
            relatedBy: NSLayoutConstraint.Relation.equal,
            toItem: nil,
            attribute: NSLayoutConstraint.Attribute.notAnAttribute,
            multiplier: 1,
            constant: 350
        )
        editRadiusAlert.view.addConstraint(height);
        editRadiusAlert.view.addSubview(pickerView)
        editRadiusAlert.addAction(UIAlertAction(title: "Done", style: .default, handler: { (alert: UIAlertAction!) -> Void in
            let lvlIndex = pickerView.selectedRow(inComponent: 0)
            self.kanjiPresenter.changeNewKanjiGroupLevel(level: Int32(5 - lvlIndex))
        }))
        editRadiusAlert.addAction(UIAlertAction(title: "Cancel", style: .cancel, handler: nil))
        self.present(editRadiusAlert, animated: true)
    }
    
    private func setupSelectors() {
        let lvlSelector = UIBarButtonItem(image: UIImage(systemName: "list.number"), style: .plain, target: self, action: #selector(selectLevel))
        let examSelector = UIBarButtonItem(
            image: UIImage(systemName: "slider.horizontal.3"),
            style: .plain,
            target: self,
            action: #selector(selectExam)
        )
        navigationItem.rightBarButtonItems = [examSelector, lvlSelector]
    }
}

extension KanjiListViewController : UIPickerViewDataSource, UIPickerViewDelegate {
    func numberOfComponents(in pickerView: UIPickerView) -> Int {
        return 1
    }
    
    func pickerView(_ pickerView: UIPickerView, numberOfRowsInComponent component: Int) -> Int {
        return levels.count
    }
    
    func pickerView(_ pickerView: UIPickerView, titleForRow row: Int, forComponent component: Int) -> String? {
        return levels[row]
    }
}
