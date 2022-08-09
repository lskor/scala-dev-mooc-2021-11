
package module4.homework.services

import module4.homework.dao.entity.{Role, RoleCode, User, UserId}
import module4.homework.dao.repository.UserRepository
import module4.phoneBook.dao.repositories.PhoneRecordRepository.ctx
import module4.phoneBook.db
import zio.{Has, RIO, ZIO, ZLayer}
import zio.macros.accessible

@accessible
object UserService{
    type UserService = Has[Service]

    trait Service{
        def listUsers(): RIO[db.DataSource, List[User]]
        def listUsersDTO(): RIO[db.DataSource, List[UserDTO]]
        def addUserWithRole(user: User, roleCode: RoleCode): RIO[db.DataSource, UserDTO]
        def listUsersWithRole(roleCode: RoleCode): RIO[db.DataSource, List[UserDTO]]
    }

    class Impl(userRepo: UserRepository.Service) extends Service{
        val dc = db.Ctx

        def listUsers(): RIO[db.DataSource, List[User]] =
            userRepo.list()

        def listUsersDTO(): RIO[db.DataSource,List[UserDTO]] = for {
            users <- userRepo.list()
            dto <- ZIO.collectAll(users.map(findUserRoles))
        } yield dto

        def addUserWithRole(user: User, roleCode: RoleCode): RIO[db.DataSource, UserDTO] = for {
            dto <- ctx.transaction(
                    for {
                      _ <- userRepo.createUser(user)
                      _ <- userRepo.insertRoleToUser(roleCode, UserId(user.id))
                      dto <- findUserRoles(user)
                    } yield dto
                )
        } yield dto

        def listUsersWithRole(roleCode: RoleCode): RIO[db.DataSource,List[UserDTO]] =  for {
            dto <- ctx.transaction(
                for {
                    users <- userRepo.listUsersWithRole(roleCode)
                    dto <- ZIO.collectAll(users.map(findUserRoles))
                } yield dto
            )
        } yield dto

        private def findUserRoles(user: User) = for {
            roles <- userRepo.userRoles(UserId(user.id))
        } yield UserDTO(user, roles.toSet)
    }

    val live: ZLayer[UserRepository.UserRepository, Nothing, UserService] =
        ZLayer.fromService[UserRepository.Service, UserService.Service](userRepo => new Impl(userRepo))
}

case class UserDTO(user: User, roles: Set[Role])