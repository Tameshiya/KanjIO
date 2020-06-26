import UIKit
import shared

class KanjiListViewController: UIViewController {
    
    var kanjiPresenter: KanjiPresenter!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        let defaults = UserDefaults.standard
        let isPreloaded = defaults.bool(forKey: "isPreloaded")
        if !isPreloaded {
            prepopulateDbBitwicely(dbName: "KanjiDb.db")
            defaults.set(true, forKey: "isPreloaded")
        }
        let db: KanjiDatabase = KanjiDatabase()
        db.defaultDriver()
        let kanjiQueries: KanjiQueries = db.instance.kanjiQueries
        let kanjiRepository: KanjiRepository = JLPTKanjiRepository(kanjiGroup: KanjiGroup.jlpt, kanjiQueries: kanjiQueries)
        let kanjiInteractor = KanjiInteractorImpl(kanjiRepository: kanjiRepository)
        self.kanjiPresenter = KanjiPresenter(interactor: kanjiInteractor)
        kanjiPresenter.attachView(view: Test(vc: self))
        kanjiPresenter.startFlow()
        //todo self.title = "EconomyStrategy"
    }
    
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

class Test: KanjiListView {
    
    let vc: KanjiListViewController
    
    init(vc: KanjiListViewController) {
        self.vc = vc
    }
    
    func setTitle(currentKanjiGroup: KanjiGroup) {
        vc.title = currentKanjiGroup.name
    }
    
    func showList(list: [Kanji_]) {
        print(list)
    }
}
