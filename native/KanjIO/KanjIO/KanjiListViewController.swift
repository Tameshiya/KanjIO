import UIKit
import shared

class KanjiListViewController: UIViewController, KanjiListView, UITableViewDelegate {
    
    private let kanjiTableView = UITableView()
    private var safeArea: UILayoutGuide!
    private var kanjiPresenter: KanjiPresenter!
    //todo privateにする必要があるけど、あらゆる手段を調べる。
    var kanjiList = [Kanji_]()
    
    override func viewDidLoad() {
        super.viewDidLoad()
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
    
    func setupTableView() {
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
}
