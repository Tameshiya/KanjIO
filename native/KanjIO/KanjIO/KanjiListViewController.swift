import UIKit
import shared

class KanjiListViewController: UIViewController, KanjiListView, UITableViewDelegate {
    
    private let kanjiTableView = UITableView()
    private var safeArea: UILayoutGuide!
    private var kanjiPresenter: KanjiPresenter!
    private var kanjiGroupsPresenter: KanjiGroupsPresenter!
    //todo privateにする必要があるけど、手段を調べる。
    var kanjiList = [Kanji_]()
    
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
        let kanjiGroupQueries: KanjiGroupsQueries = db.instance.kanjiGroupsQueries
        let groupLevelQueries: GroupsLevelsQueries = db.instance.groupsLevelsQueries
        let kanjiRepository: KanjiRepository = KanjiRepositoryImpl(kanjiQueries: kanjiQueries)
        let kanjiGroupRepository: KanjiGroupRepository = KanjiGroupRepositoryImpl(
            kanjiGroupsQueries: kanjiGroupQueries,
            groupsLevelsQueries: groupLevelQueries
        )
        let kanjiInteractor: KanjiInteractor = KanjiInteractorImpl(kanjiRepository: kanjiRepository, groupRepository: kanjiGroupRepository)
        let userRepository: UserRepository = UserRepositoryImpl(kanjiPreferences: KanjiPreferences())
        let userInteractor: UserInteractor = UserInteractorImpl(userRepository: userRepository, groupRepository: kanjiGroupRepository)
        self.kanjiGroupsPresenter = KanjiGroupsPresenter(userInteractor: userInteractor, kanjiInteractor: kanjiInteractor)
        self.kanjiPresenter = KanjiPresenter(kanjiInteractor: kanjiInteractor, userInteractor: userInteractor)
        kanjiPresenter.attachView(view: self)
        kanjiGroupsPresenter.onKanjiGroupLevelSelectedListener = { (kanjiGroupLevel: KanjiGroupLevel) -> Void in
            self.kanjiPresenter.renderCurrentKanjiList(kanjiLevel: kanjiGroupLevel)
        }
        kanjiPresenter.startFlow()
    }
    
    func setTitle(kanjiGroupTitle: String) {
        title = kanjiGroupTitle
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
    
    @objc private func selectExam() {
        showExamPickerView()
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
    
    private func showExamPickerView() {
        let _ = KanjiGroupDialogView(kanjiGroupPresenter: kanjiGroupsPresenter, kanjiListViewController: self)
    }
    
    private func setupSelectors() {
        let examSelector = UIBarButtonItem(
            image: UIImage(systemName: "list.number"),
            style: .plain,
            target: self,
            action: #selector(selectExam)
        )
        navigationItem.rightBarButtonItems = [examSelector]
    }
}
