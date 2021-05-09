import React, {Component} from 'react';
import CrawlerService from "../services/CrawlerService";

class ResultsComponent extends Component {
    constructor(props) {
        super(props);

        this.state = {
            id: this.props.match.params.id,
            termsStats: []
        }

    }

    componentDidMount() {
        CrawlerService.getTermsStatsForAllLinks().then((res) => {
            this.setState({ termStats: res.data});
        });
    }

    render() {
        return (
            <div>
                <h2 className="text-center">Links</h2>
                <div className="row">
                    <table className="table table-striped table-bordered">

                        <thead>
                        <tr>
                            <th>URL</th>
                            {this.state.termsStats.wordCounterBean.map(
                                wordCounter =>
                                    <th>{wordCounter.word}</th>
                            )}
                            <th>Total</th>
                        </tr>
                        </thead>


                        <tbody>
                        {
                            this.state.termsStats.map(
                                termStats =>
                                    <tr>
                                        <td>{termStats.link}</td>
                                        {termStats.wordCounterBean.map(
                                            wordCounter =>
                                                <th>{wordCounter.count}</th>
                                        )}
                                        <td>{termStats.total}</td>
                                        <td>
                                        </td>
                                    </tr>
                            )
                        }
                        </tbody>
                    </table>
                </div>
            </div>
        );
    }
}

export default ResultsComponent;
