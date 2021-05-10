import React, {Component} from 'react';
import CrawlerService from "../services/CrawlerService";

class ResultsComponent extends Component {
    constructor(props) {
        super(props);

        this.state = {
            id: this.props.match.params.id,
            seedLink: this.props.match.params.seedLink,
            termsStats: []
        }

    }

    componentDidMount() {
        CrawlerService.getTermsStatsForTopLinks(this.state.seedLink, this.state.id).then((res) => {
            this.setState({ termsStats: res.data});
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
                            {this.state.termsStats[0]}
                            <th>Total</th>
                        </tr>
                        </thead>


                        <tbody>
                        {
                            this.state.termsStats.map(
                                termStats =>
                                    <tr>
                                        <td>{termStats.link}</td>
                                        {termStats.wordCounterBeans.map(
                                            wordCounterBean =>
                                                <th>{wordCounterBean.count}</th>
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
