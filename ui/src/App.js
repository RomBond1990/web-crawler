import './App.css';
import {HashRouter, Route, Switch} from 'react-router-dom'
import CreateCrawlerSettingComponent from "./components/CreateCrawlerSettingComponent";
import ResultsComponent from "./components/ResultsComponent";
import HeaderComponent from "./components/HeaderComponent";

function App() {
    return (<div>
            <HashRouter>
                <HeaderComponent/>
                <div className="container">
                    <Switch>
                        <Route path="/" exact component={CreateCrawlerSettingComponent}/>
                        <Route path="/api/crawlers/:seedLink/:id" component={ResultsComponent}/>
                    </Switch>
                </div>
            </HashRouter>
        </div>
    );
}

export default App;
