import './App.css';
import {BrowserRouter, Route, Switch} from 'react-router-dom'
import CreateCrawlerSettingComponent from "./components/CreateCrawlerSettingComponent";
import ResultsComponent from "./components/ResultsComponent";

function App() {
    return (<div>
            <BrowserRouter>
                <div className="container">
                    <Switch>
                        <Route path="/" exact component={CreateCrawlerSettingComponent}/>
                        <Route path="/api/crawlers/:seedLink/:id" component={ResultsComponent}/>
                    </Switch>
                </div>
            </BrowserRouter>
        </div>
    );
}

export default App;
