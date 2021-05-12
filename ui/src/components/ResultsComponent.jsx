import React, {Component} from 'react';
import CrawlerService from "../services/CrawlerService";
import TermList from "./TermList";


class ResultsComponent extends Component {
    constructor(props) {
        super(props);

        this.state = {
            seedLink: this.props.match.params.seedLink,
            id: this.props.match.params.id
        }

        this.generateAllLinks = this.generateAllLinks.bind(this);
        this.generateTopLinks = this.generateTopLinks.bind(this);
    }

    generateAllLinks = (event) => {
        event.preventDefault();
        CrawlerService.getTermsStatsForAllLinks(this.state.seedLink).then(res => {
            this.props.history.push('/api/crawlers' + '/' + this.state.seedLink + "/10");
        })
    }

    generateTopLinks = (event) => {
        event.preventDefault();
        CrawlerService.getTermsStatsForTopLinks(this.state.seedLink, this.state.id).then(res => {
            this.props.history.push('/api/crawlers' + '/' + this.state.seedLink + "/10");
        })
    }

    render() {
        return (
            <div>
                <div className="content">
                        <div className="row" style={{marginTop: 20}}>
                            <div className="col-sm-1"></div>
                            <div className="col-sm-5">
                                <div className="card">
                                    <div className="card-header text-center">Get CSV</div>
                                    <div className="card-body">
                                        <div className="row">
                                            <button className="btn btn-success"
                                                    onClick={this.generateAllLinks}>All Data
                                            </button>
                                        </div>
                                    </div>
                                    <div className="card-body">
                                        <div className="row">
                                            <button className="btn btn-success" onClick={this.generateTopLinks}>Top 10
                                                Links
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                </div>

            </div>


        );
    }
}


export default ResultsComponent;