package tsuzuru.useCase.command

import tsuzuru.common.useCase.command.AbstractCommand
import tsuzuru.domain.entity.UserEntity

interface UserModifiesProfileCommand
    : AbstractCommand<UserModifiesProfileCommand.Request, UserModifiesProfileCommand.Response> {

    class Request(
        val profileName: String,
        val scope: UserEntity.Scope,
    ) : AbstractCommand.Request

    class Response : AbstractCommand.Response

}
