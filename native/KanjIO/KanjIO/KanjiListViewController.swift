import UIKit
import shared

class KanjiListViewController: UIViewController {
    
    var kanjiPresenter: KanjiPresenter? = nil
    
    override func viewDidLoad() {
        super.viewDidLoad()
        let defaults = UserDefaults.standard
        let isPreloaded = defaults.bool(forKey: "isPreloaded")
        if !isPreloaded {
           prepopulateDb()
           defaults.set(true, forKey: "isPreloaded")
        }
        let db: KanjiDatabase = KanjiDatabase()
        db.defaultDriver()
        let kanjiQueries: KanjiQueries = db.instance.kanjiQueries
        let kanjiRepository: KanjiRepository = JLPTKanjiRepository(kanjiGroup: KanjiGroup.jlpt, kanjiQueries: kanjiQueries)
        let kanjiInteractor = KanjiInteractorImpl(kanjiRepository: kanjiRepository)
        self.kanjiPresenter = KanjiPresenter(interactor: kanjiInteractor)
        kanjiPresenter?.attachView(view: Test(vc: self))
        //todo start flow (remove suspended functions)
        //todo self.title = "EconomyStrategy"
    }
    
    deinit {
        
    }
    
    func prepopulateDb() {
        let databasePath = Bundle.main.url(forResource: "KanjiDb", withExtension:"db");
        var documentsDirectory: URL
        do {
            documentsDirectory = try FileManager.default.url(
                    for: FileManager.SearchPathDirectory.documentDirectory,
                    in:FileManager.SearchPathDomainMask.userDomainMask,
                    appropriateFor:nil,
                    create:false
            );
            let source = databasePath
            let destination: URL? = documentsDirectory
            if (destination != nil && source != nil) {
                let destination2 = destination?.appendingPathComponent("KanjiDb.db")
                var _ = try FileManager.default.copyItem(at:source!, to:destination2!)
            }
        } catch {
            print("終わりだ。")
        }
        
    }
}

class Test: KanjiListView {
    
    let wc: KanjiListViewController
    
    init(vc: KanjiListViewController) {
        self.wc = vc
    }
    
    func setTitle(currentKanjiGroup: KanjiGroup) {
        wc.title = currentKanjiGroup.name
    }
    
    func showList(list: [Kanji_]) {
        print("list: $list")
    }
}

