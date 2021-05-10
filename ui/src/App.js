import logo from './logo.svg';
import './App.css';
import {BrowserRouter, Route, Switch} from 'react-router-dom'
import ResultsComponent from "./components/ResultsComponent";
import HeaderComponent from "./components/HeaderComponent";
import FooterComponent from "./components/FooterComponent";

function App() {
  return (<div>
        <BrowserRouter>
          <HeaderComponent/>
          <div className="container">
            <Switch>
              {/*<Route path = "/" exact component = {ResultsComponent}/>*/}
              <Route path = "/api/crawlers/:seedLink/:id" component = {ResultsComponent}/>
            </Switch>
          </div>
          <FooterComponent/>
        </BrowserRouter>
      </div>
  );
}

export default App;
